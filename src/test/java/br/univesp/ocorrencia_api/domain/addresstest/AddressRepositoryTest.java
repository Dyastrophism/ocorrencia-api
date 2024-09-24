package br.univesp.ocorrencia_api.domain.addresstest;

import static br.univesp.ocorrencia_api.common.AddressConstants.ADDRESS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.univesp.ocorrencia_api.entity.Address;
import br.univesp.ocorrencia_api.repository.AddressRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup() {
        ADDRESS.setId(null);
    }

    @AfterEach
    void tearDown() {
        testEntityManager.clear();
    }

    @Test
    void save_WithValidData_ReturnAddress() {
        addressRepository.save(ADDRESS);
        Address result = testEntityManager.find(Address.class, ADDRESS.getId());
        assertThat(result).isEqualTo(ADDRESS);
    }

    @Test
    void save_WithInvalidData_ThrowsException() {
        Address address = new Address();
        assertThatThrownBy(() -> addressRepository.save(address)).isInstanceOf(Exception.class);
    }

    @Test
    void update_WithValidData_ReturnAddress() {
        testEntityManager.persist(ADDRESS);
        Address address = new Address(ADDRESS.getId(), "Rua dos Bobos", "Apt 101", "12345678", "São Paulo", "SP");
        addressRepository.save(address);
        Address result = testEntityManager.find(Address.class, ADDRESS.getId());
        assertThat(result).isEqualTo(address);
    }

    @Test
    void update_WithInvalidData_ThrowsException() {
        Address address = new Address();
        assertThatThrownBy(() -> addressRepository.save(address)).isInstanceOf(Exception.class);
    }

    @Test
    void delete_WithValidData_ReturnAddress() {
        testEntityManager.persist(ADDRESS);
        addressRepository.delete(ADDRESS);
        Address result = testEntityManager.find(Address.class, ADDRESS.getId());
        assertThat(result).isNull();
    }
}
