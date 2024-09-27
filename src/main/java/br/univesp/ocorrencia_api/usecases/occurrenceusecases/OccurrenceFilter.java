package br.univesp.ocorrencia_api.usecases.occurrenceusecases;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record OccurrenceFilter(
        String clientName,
        @Size(min = 11, max = 11, message = "Only numbers. Ex: 22222222222")
        String clientCpf,
        LocalDate occurrenceDate,
        String cityName
) {
}
