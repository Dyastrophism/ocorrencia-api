package br.univesp.ocorrencia_api.service;

import br.univesp.ocorrencia_api.entity.Address;
import br.univesp.ocorrencia_api.repository.AddressRepository;
import br.univesp.ocorrencia_api.usecases.addressusecases.AddressRequest;
import br.univesp.ocorrencia_api.usecases.addressusecases.AddressResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressResponse createAddress(AddressRequest addressRequest) {
        Address address = Address.fromRequest(
                addressRequest.publicPlace(),
                addressRequest.neighborhood(),
                addressRequest.zipCode(),
                addressRequest.city(),
                addressRequest.state());
        addressRepository.save(address);
        return AddressResponse.fromEntity(address);
    }

    public Page<AddressResponse> findAllAddresses(Pageable pageable) {
        return addressRepository.findAll(pageable).map(AddressResponse::fromEntity);
    }

    public Optional<AddressResponse> findAddressById(Long id) {
        return addressRepository.findById(id).map(AddressResponse::fromEntity);
    }

    public AddressResponse updateAddress(Long id, AddressRequest addressRequest) {
        Address address = addressRepository.findById(id).orElseThrow();
        address.setPublicPlace(addressRequest.publicPlace());
        address.setNeighborhood(addressRequest.neighborhood());
        address.setZipCode(addressRequest.zipCode());
        address.setCity(addressRequest.city());
        address.setState(addressRequest.state());
        addressRepository.save(address);
        return AddressResponse.fromEntity(address);
    }

    public void deleteAddress(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
        } else {
            throw new RuntimeException("Address not found");
        }

    }
}
