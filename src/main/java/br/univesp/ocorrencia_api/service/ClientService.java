package br.univesp.ocorrencia_api.service;

import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.exception.DuplicatedCpfException;
import br.univesp.ocorrencia_api.repository.ClientRepository;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientResponseDTO;
import br.univesp.ocorrencia_api.usecases.clientusecases.CreateClientDTO;
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

    public ClientResponseDTO create(CreateClientDTO createClientDTO) {
        if (clientRepository.existsByCpf(createClientDTO.cpf())) {
            throw new DuplicatedCpfException("Client already exists");
        }
        Client client = Client.fromRequest(createClientDTO.name(), createClientDTO.birthDate(), createClientDTO.cpf());
        clientRepository.save(client);
        return ClientResponseDTO.fromEntity(client);
    }

    public Page<ClientResponseDTO> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable).map(ClientResponseDTO::fromEntity);
    }

    public Optional<ClientResponseDTO> findById(Long id) {
        return clientRepository.findById(id).map(ClientResponseDTO::fromEntity);
    }

    public ClientResponseDTO update(Long id, CreateClientDTO createClientDTO) {
        Client client = clientRepository.findById(id).orElseThrow();
        if (clientRepository.existsByCpf(createClientDTO.cpf())) {
            throw new DuplicatedCpfException("Client already exists");
        }
        client.setName(createClientDTO.name());
        client.setBirthDate(createClientDTO.birthDate());
        client.setCpf(createClientDTO.cpf());
        clientRepository.save(client);
        return ClientResponseDTO.fromEntity(client);
    }

    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
