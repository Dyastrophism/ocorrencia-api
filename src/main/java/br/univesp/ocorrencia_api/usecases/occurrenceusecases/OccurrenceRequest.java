package br.univesp.ocorrencia_api.usecases.occurrenceusecases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record OccurrenceRequest(
        @NotBlank(message = "Client name is required")
        String clientName,
        @NotBlank(message = "Client cpf is required")
        @Size(min = 11, max = 11, message = "Only numbers. Ex: 22222222222")
        String clientCpf,
        @NotBlank(message = "Public place is required")
        String publicPlace,
        @NotBlank(message = "Neighborhood is required")
        String neighborhood,
        @NotBlank(message = "Zip code is required")
        @Size(min = 8, max = 8, message = "Only numbers. Ex: 22222222")
        String zipCode,
        @NotBlank(message = "City is required")
        String city,
        @NotBlank(message = "State is required")
        @Size(min = 2, max = 2, message = "Only letters. Ex: SP")
        String state,
        List<MultipartFile> images

) {
}
