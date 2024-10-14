package br.univesp.ocorrencia_api.usecases.clientusecases;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ClientRequest(
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Birth date is required")
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate birthDate,
        @NotBlank(message = "CPF is required")
        @Size(min = 11, max = 11, message = "Only numbers. Ex: 22222222222")
        String cpf
) {
}
