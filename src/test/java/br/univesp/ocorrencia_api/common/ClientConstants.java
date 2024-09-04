package br.univesp.ocorrencia_api.common;

import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.usecases.clientusecases.CreateClientDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientConstants {

    public static final CreateClientDTO CLIENT_DTO = new CreateClientDTO("Client Name", LocalDate.of(2000, 1, 1), "12345678909");
    public static final CreateClientDTO INVALID_CLIENT_DTO = new CreateClientDTO(null, null, null);
    public static final Client CLIENT = new Client(1L, CLIENT_DTO.name(), CLIENT_DTO.birthDate(), CLIENT_DTO.cpf(), LocalDateTime.now());
}
