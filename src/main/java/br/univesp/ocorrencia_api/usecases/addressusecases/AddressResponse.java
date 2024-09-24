package br.univesp.ocorrencia_api.usecases.addressusecases;

import br.univesp.ocorrencia_api.entity.Address;

public record AddressResponse(
        String publicPlace,
        String neighborhood,
        String zipCode,
        String city,
        String state
) {

    public static AddressResponse fromEntity(Address address) {
        return new AddressResponse(
                address.getPublicPlace(),
                address.getNeighborhood(),
                address.getZipCode(),
                address.getCity(),
                address.getState()
        );
    }
}
