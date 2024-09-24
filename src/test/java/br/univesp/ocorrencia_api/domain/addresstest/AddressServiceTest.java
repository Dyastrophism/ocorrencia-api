package br.univesp.ocorrencia_api.domain.addresstest;

import br.univesp.ocorrencia_api.entity.Address;
import br.univesp.ocorrencia_api.repository.AddressRepository;
import br.univesp.ocorrencia_api.service.AddressService;
import br.univesp.ocorrencia_api.usecases.addressusecases.AddressResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static br.univesp.ocorrencia_api.common.AddressConstants.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @Test
    void createAddress_WithValidData_ReturnsAddress() {
        Address address = Address.fromRequest(ADDRESS_REQUEST.publicPlace(), ADDRESS_REQUEST.neighborhood(), ADDRESS_REQUEST.city(), ADDRESS_REQUEST.state(), ADDRESS_REQUEST.zipCode());
        when(addressRepository.save(address)).thenReturn(address);
        AddressResponse sut = addressService.createAddress(ADDRESS_REQUEST);
        assertThat(sut).isEqualTo(AddressResponse.fromEntity(address));
    }

    @Test
    void createAddress_WithInvalidData_ThrowsException() {
        when(addressRepository.save(Address.fromRequest(INVALID_ADDRESS_REQUEST.publicPlace(), INVALID_ADDRESS_REQUEST.neighborhood(), INVALID_ADDRESS_REQUEST.city(), INVALID_ADDRESS_REQUEST.state(), INVALID_ADDRESS_REQUEST.zipCode()))).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> addressService.createAddress(INVALID_ADDRESS_REQUEST)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void findAllAddresses_ReturnPageOfAddresses() {
        when(addressRepository.findAll(Pageable.unpaged())).thenReturn(new PageImpl<>(List.of(ADDRESS)));
        Page<AddressResponse> addressPage = addressService.findAllAddresses(Pageable.unpaged());
        assertThat(addressPage).isNotEmpty().hasSize(1).contains(AddressResponse.fromEntity(ADDRESS));
    }

    @Test
    void findAllAddresses_ReturnEmptyPage() {
        when(addressRepository.findAll(Pageable.unpaged())).thenReturn(Page.empty());
        Page<AddressResponse> addressPage = addressService.findAllAddresses(Pageable.unpaged());
        assertThat(addressPage).isEmpty();
    }

    @Test
    void findAddressById_WithValidId_ReturnAddress() {
        when(addressRepository.findById(ADDRESS.getId())).thenReturn(java.util.Optional.of(ADDRESS));
        Optional<AddressResponse> sut = addressService.findAddressById(ADDRESS.getId());
        assertThat(sut).isPresent().contains(AddressResponse.fromEntity(ADDRESS));
    }

    @Test
    void findAddressById_WithInvalidId_returnEmpty() {
        when(addressRepository.findById(99L)).thenReturn(Optional.empty());
        assertThat(addressService.findAddressById(99L)).isEmpty();
    }

    @Test
    void updateAddress_WithValidData_ReturnsAddress() {
        when(addressRepository.save(any())).thenReturn(ADDRESS);
        AddressResponse sut = addressService.updateAddress(ADDRESS.getId(), ADDRESS_REQUEST);
        assertThat(sut).isEqualTo(AddressResponse.fromEntity(ADDRESS));
    }

    @Test
    void updateAddress_WithInvalidData_ThrowsRuntimeException() {
        when(addressRepository.save(any())).thenThrow(RuntimeException.class);
        assertThatThrownBy(() -> addressService.updateAddress(ADDRESS.getId(), ADDRESS_REQUEST)).isInstanceOf(RuntimeException.class);
    }

    @Test
    void deleteAddress_WithValidId_ReturnsVoid() {
        assertThatCode(() -> addressService.deleteAddress(ADDRESS.getId())).doesNotThrowAnyException();
    }

    @Test
    void deleteAddress_WithInvalidId_ThrowsRuntimeException() {
        when(addressRepository.existsById(99L)).thenReturn(false);
        assertThatThrownBy(() -> addressService.deleteAddress(99L)).isInstanceOf(RuntimeException.class);
    }

}
