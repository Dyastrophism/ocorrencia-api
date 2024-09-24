package br.univesp.ocorrencia_api.usecases.clientusecases;

import br.univesp.ocorrencia_api.entity.Client;

import java.time.LocalDate;

public record ClientResponse(
        String name,
        LocalDate birthDate,
        String cpf
) {

    public static ClientResponse fromEntity(Client client) {
        return new ClientResponse(
                client.getName(),
                client.getBirthDate(),
                client.getCpf()
        );
    }
}
