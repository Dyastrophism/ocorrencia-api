package br.univesp.ocorrencia_api.usecases.clientusecases;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record ClientRequest(
        @NotBlank
        String name,
        @NotBlank
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate birthDate,
        @NotBlank
        @Pattern(regexp = "\\d{11}")
        String cpf
) {
}
