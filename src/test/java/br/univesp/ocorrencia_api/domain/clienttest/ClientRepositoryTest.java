package br.univesp.ocorrencia_api.domain.clienttest;

import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.repository.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static br.univesp.ocorrencia_api.common.ClientConstants.CLIENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setUp() {
        CLIENT.setId(null);
    }

    @AfterEach
    void tearDown() {
        testEntityManager.clear();
    }

    @Test
    void createClient_WithValidData_ReturnClient() {
        Client client = clientRepository.save(CLIENT);
        Client savedClient = testEntityManager.find(Client.class, client.getId());
        assertThat(savedClient).isEqualTo(client);
    }

    @Test
    void createClient_WithInvalidData_ThrowsException() {
        Client client = new Client();
        assertThatThrownBy(() -> clientRepository.save(client)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void createClient_WithExistingCpf_ThrowsException() {
        testEntityManager.persistAndFlush(CLIENT);
        testEntityManager.detach(CLIENT);
        CLIENT.setId(null);
        CLIENT.setCpf("12345678909");
        assertThatThrownBy(() -> clientRepository.save(CLIENT)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void updateClient_WithValidData_ReturnClient() {
        Client client = testEntityManager.persistAndFlush(CLIENT);
        client.setName("Updated Name");
        client.setCpf("12345678909");
        client.setBirthDate(client.getBirthDate().plusDays(1));
        Client updatedClient = clientRepository.save(client);
        assertThat(updatedClient).isEqualTo(client);
    }

    @Test
    void getClient_WithExistingId_ReturnClient() {
        Client client = testEntityManager.persistAndFlush(CLIENT);
        Client foundClient = clientRepository.findById(client.getId()).orElseThrow();
        assertThat(foundClient).isEqualTo(client);
    }

    @Test
    void getClient_WithUnexistingId_ReturnEmpty() {
        assertThat(clientRepository.findById(99L)).isEmpty();
    }
} 
