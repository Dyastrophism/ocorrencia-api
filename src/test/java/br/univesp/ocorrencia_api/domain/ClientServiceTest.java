package br.univesp.ocorrencia_api.domain;

import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.repository.ClientRepository;
import br.univesp.ocorrencia_api.service.ClientService;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.univesp.ocorrencia_api.common.ClientConstants.CLIENT_DTO;
import static br.univesp.ocorrencia_api.common.ClientConstants.INVALID_CLIENT_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void createClient_WithValidData_ReturnClient() {
        Client client = Client.fromRequest(CLIENT_DTO.name(), CLIENT_DTO.birthDate(), CLIENT_DTO.cpf());
        when(clientRepository.save(client)).thenReturn(client);
        ClientResponseDTO sut = clientService.create(CLIENT_DTO);
        assertThat(sut).isEqualTo(ClientResponseDTO.fromEntity(client));
    }

    @Test
    void createClient_WithInvalidData_ThrowsException() {
        when(clientRepository.save(Client.fromRequest(INVALID_CLIENT_DTO.name(), INVALID_CLIENT_DTO.birthDate(), INVALID_CLIENT_DTO.cpf()))).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> clientService.create(INVALID_CLIENT_DTO)).isInstanceOf(RuntimeException.class);
    }
}
