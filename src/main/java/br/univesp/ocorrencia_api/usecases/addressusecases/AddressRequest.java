package br.univesp.ocorrencia_api.usecases.addressusecases;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank(message = "Public place is required")
        String publicPlace,
        @NotBlank(message = "Neighborhood is required")
        String neighborhood,
        @NotBlank(message = "Zip code is required")
        String zipCode,
        @NotBlank(message = "City is required")
        String city,
        @NotBlank(message = "State is required")
        String state) {
}
