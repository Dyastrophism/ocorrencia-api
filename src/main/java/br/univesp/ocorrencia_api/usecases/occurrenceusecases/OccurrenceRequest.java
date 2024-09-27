package br.univesp.ocorrencia_api.usecases.occurrenceusecases;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record OccurrenceRequest(
        @NotBlank
        String clientName,
        @NotBlank
        @Size(min = 11, max = 11, message = "Only numbers. Ex: 22222222222")
        String clientCpf,
        @NotBlank
        String publicPlace,
        @NotBlank
        String neighborhood,
        @NotBlank
        @Size(min = 8, max = 8, message = "Only numbers. Ex: 22222222")
        String zipCode,
        @NotBlank
        String city,
        @NotBlank
        @Size(min = 2, max = 2, message = "Only letters. Ex: SP")
        String state,
        List<MultipartFile> images

) {
}
