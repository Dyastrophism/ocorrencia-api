package br.univesp.ocorrencia_api.common;

import br.univesp.ocorrencia_api.entity.Address;
import br.univesp.ocorrencia_api.usecases.addressusecases.AddressRequest;

public class AddressConstants {

    public static final AddressRequest ADDRESS_REQUEST = new AddressRequest("Rua dos Bobos", "Apt 101", "12345678", "São Paulo", "SP");
    public static final AddressRequest INVALID_ADDRESS_REQUEST = new AddressRequest(null, null, null, null, null);
    public static final Address ADDRESS = new Address(1L, ADDRESS_REQUEST.publicPlace(), ADDRESS_REQUEST.neighborhood(), ADDRESS_REQUEST.zipCode(), ADDRESS_REQUEST.city(), ADDRESS_REQUEST.state());
}
