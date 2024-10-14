package br.univesp.ocorrencia_api.service;

import br.univesp.ocorrencia_api.entity.Address;
import br.univesp.ocorrencia_api.exception.AddressNotFoundException;
import br.univesp.ocorrencia_api.repository.AddressRepository;
import br.univesp.ocorrencia_api.usecases.addressusecases.AddressRequest;
import br.univesp.ocorrencia_api.usecases.addressusecases.AddressResponse;
import br.univesp.ocorrencia_api.usecases.occurrenceusecases.OccurrenceRequest;
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

    /**
     * Create address
     * @param addressRequest address request
     * @return created address
     */
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

    /**
     * Find all addresses
     * @param pageable pageable
     * @return all addresses pageable
     */
    public Page<AddressResponse> findAllAddresses(Pageable pageable) {
        return addressRepository.findAll(pageable).map(AddressResponse::fromEntity);
    }

    /**
     * Find address by id
     * @param id address id
     * @return address found
     */
    public Optional<AddressResponse> findAddressById(Long id) {
        if (addressRepository.existsById(id)) {
            return addressRepository.findById(id).map(AddressResponse::fromEntity);
        } else {
            throw new AddressNotFoundException("Address not found");
        }
    }

    /**
     * Update address by id
     * @param id address id
     * @param addressRequest new address data
     * @return address updated address
     */
    public AddressResponse updateAddress(Long id, AddressRequest addressRequest) {
        if (!addressRepository.existsById(id)) {
            throw new AddressNotFoundException("Address not found to update");
        }
        Address address = addressRepository.findById(id).orElseThrow();
        address.setPublicPlace(addressRequest.publicPlace());
        address.setNeighborhood(addressRequest.neighborhood());
        address.setZipCode(addressRequest.zipCode());
        address.setCity(addressRequest.city());
        address.setState(addressRequest.state());
        addressRepository.save(address);
        return AddressResponse.fromEntity(address);
    }

    /**
     * Delete address by id
     * @param id address id
     */
    public void deleteAddress(Long id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
        } else {
            throw new AddressNotFoundException("Address not found");
        }

    }

    /**
     * Find address by occurrence request
     * @param occurrenceRequest occurrence request
     * @return address found
     */
    public Address findByOccurrenceRequest(OccurrenceRequest occurrenceRequest) {
        return addressRepository.findByPublicPlaceAndNeighborhoodAndZipCode(
                occurrenceRequest.publicPlace(),
                occurrenceRequest.neighborhood(),
                occurrenceRequest.zipCode()
        ).orElseThrow();
    }
}
