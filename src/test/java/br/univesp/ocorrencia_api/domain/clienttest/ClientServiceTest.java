package br.univesp.ocorrencia_api.domain.clienttest;

import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.repository.ClientRepository;
import br.univesp.ocorrencia_api.service.ClientService;
import br.univesp.ocorrencia_api.usecases.clientusecases.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static br.univesp.ocorrencia_api.common.ClientConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Test
    void createClient_WithValidData_ReturnClient() {
        Client client = Client.fromRequest(CLIENT_RESQUEST.name(), CLIENT_RESQUEST.birthDate(), CLIENT_RESQUEST.cpf());
        when(clientRepository.save(client)).thenReturn(client);
        ClientResponse sut = clientService.create(CLIENT_RESQUEST);
        assertThat(sut).isEqualTo(ClientResponse.fromEntity(client));
    }

    @Test
    void createClient_WithInvalidData_ThrowsException() {
        when(clientRepository.save(Client.fromRequest(INVALID_CLIENT_REQUEST.name(), INVALID_CLIENT_REQUEST.birthDate(), INVALID_CLIENT_REQUEST.cpf()))).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> clientService.create(INVALID_CLIENT_REQUEST)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void findAllClients_ReturnPageOfClients() {
        when(clientRepository.findAll(Pageable.unpaged())).thenReturn(new PageImpl<>(List.of(CLIENT)));
        Page<ClientResponse> clientPage = clientService.findAll(Pageable.unpaged());
        assertThat(clientPage).isNotEmpty().hasSize(1).contains(ClientResponse.fromEntity(CLIENT));
    }

    @Test
    void findAllClients_ReturnEmptyPage() {
        when(clientRepository.findAll(Pageable.unpaged())).thenReturn(Page.empty());
        Page<ClientResponse> clientPage = clientService.findAll(Pageable.unpaged());
        assertThat(clientPage).isEmpty();
    }

    @Test
    void findById_WithValidId_ReturnClient() {
        when(clientRepository.findById(CLIENT.getId())).thenReturn(java.util.Optional.of(CLIENT));
        ClientResponse client = clientService.findById(CLIENT.getId()).orElseThrow();
        assertThat(client).isEqualTo(ClientResponse.fromEntity(CLIENT));
    }

    @Test
    void findById_WithInvalidId_ReturnEmpty() {
        when(clientRepository.findById(99L)).thenReturn(java.util.Optional.empty());
        assertThat(clientService.findById(99L)).isEmpty();
    }

    @Test
    void updateClient_WithValidData_ReturnsClient() {
        when(clientRepository.save(any())).thenReturn(CLIENT);
        ClientResponse client = clientService.update(1L, CLIENT_RESQUEST);
        assertThat(client).isEqualTo(ClientResponse.fromEntity(CLIENT));
    }

    @Test
    void updateClient_WithInvalidData_ThrowsRuntimeException() {
        when(clientRepository.save(any())).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> clientService.update(1L, CLIENT_RESQUEST)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteClient_WithValidId_ReturnVoid() {
        assertThatCode(() -> clientService.delete(1L)).doesNotThrowAnyException();
    }

    @Test
    void deleteClient_WithInvalidId_ThrowsException() {
        doThrow(RuntimeException.class).when(clientRepository).deleteById(99L);
        assertThatThrownBy(() -> clientService.delete(99L)).isInstanceOf(RuntimeException.class);
    }

}
