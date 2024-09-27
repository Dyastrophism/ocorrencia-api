package br.univesp.ocorrencia_api.service;

import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.exception.ClientNotFoundException;
import br.univesp.ocorrencia_api.exception.DuplicatedCpfException;
import br.univesp.ocorrencia_api.repository.ClientRepository;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientResponse;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientRequest;
import br.univesp.ocorrencia_api.usecases.occurrenceusecases.OccurrenceRequest;
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

    /**
     * Create client
     * @param clientRequest client request
     * @return created client
     * @throws DuplicatedCpfException if client already exists
     */
    public ClientResponse create(ClientRequest clientRequest) {
        if (clientRepository.existsByCpf(clientRequest.cpf())) {
            throw new DuplicatedCpfException("Client already exists");
        }
        Client client = Client.fromRequest(clientRequest.name(), clientRequest.birthDate(), clientRequest.cpf());
        clientRepository.save(client);
        return ClientResponse.fromEntity(client);
    }

    /**
     * Find all clients
     * @param pageable pageable
     * @return all clients pageable
     */
    public Page<ClientResponse> findAll(Pageable pageable) {
        Page<Client> clients = clientRepository.findAll(pageable);
        if (clients.isEmpty()) {
            return Page.empty(pageable);
        }
        return clients.map(ClientResponse::fromEntity);
    }

    /**
     * Find client by id
     * @param id client id
     * @return client
     * @throws ClientNotFoundException if client not found
     */
    public Optional<ClientResponse> findById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new ClientNotFoundException("Client not found");
        }
        return clientRepository.findById(id).map(ClientResponse::fromEntity);
    }

    /**
     * Update client by id
     * @param id client id
     * @param clientRequest client request
     * @return client response
     * @throws DuplicatedCpfException if client already exists
     */
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

    /**
     * Delete client by id
     * @param id client id
     * @throws ClientNotFoundException if client not found
     */
    public void delete(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        } else {
            throw new ClientNotFoundException("Client not found");
        }

    }

    /**
     *  Find client by occurrence request
     * @param occurrenceRequest occurrence request
     * @return client
     * @throws ClientNotFoundException if client not found
     */
    public Client findByOccurrenceRequest(OccurrenceRequest occurrenceRequest) {
        return clientRepository.findByNameAndCpf(
                occurrenceRequest.clientName(),
                occurrenceRequest.clientCpf()
        ).orElseThrow();
    }
}
