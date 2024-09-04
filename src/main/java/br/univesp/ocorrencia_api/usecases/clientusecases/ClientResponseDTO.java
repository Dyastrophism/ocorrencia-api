package br.univesp.ocorrencia_api.usecases.clientusecases;

import br.univesp.ocorrencia_api.entity.Client;

import java.time.LocalDate;

public record ClientResponseDTO(
        String name,
        LocalDate birthDate,
        String cpf
) {

    public static ClientResponseDTO fromEntity(Client client) {
        return new ClientResponseDTO(
                client.getName(),
                client.getBirthDate(),
                client.getCpf()
        );
    }
}
