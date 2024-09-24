package br.univesp.ocorrencia_api.service;

import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.exception.DuplicatedCpfException;
import br.univesp.ocorrencia_api.repository.ClientRepository;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientResponse;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ClientResponse create(ClientRequest clientRequest) {
        if (clientRepository.existsByCpf(clientRequest.cpf())) {
            throw new DuplicatedCpfException("Client already exists");
        }
        Client client = Client.fromRequest(clientRequest.name(), clientRequest.birthDate(), clientRequest.cpf());
        clientRepository.save(client);
        return ClientResponse.fromEntity(client);
    }

    public Page<ClientResponse> findAll(Pageable pageable) {
        Page<Client> clients = clientRepository.findAll(pageable);
        if (clients.isEmpty()) {
            return Page.empty(pageable);
        }
        return clients.map(ClientResponse::fromEntity);
    }

    public Optional<ClientResponse> findById(Long id) {
        return clientRepository.findById(id).map(ClientResponse::fromEntity);
    }

    public ClientResponse update(Long id, ClientRequest clientRequest) {
        Client client = clientRepository.findById(id).orElseThrow();
        if (clientRepository.existsByCpf(clientRequest.cpf())) {
            throw new DuplicatedCpfException("Client already exists");
        }
        client.setName(clientRequest.name());
        client.setBirthDate(clientRequest.birthDate());
        client.setCpf(clientRequest.cpf());
        clientRepository.save(client);
        return ClientResponse.fromEntity(client);
    }

    public void delete(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new RuntimeException("Client not found");
        }

    }
}
