package br.univesp.ocorrencia_api.common;

import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.usecases.clientusecases.ClientRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ClientConstants {

    public static final ClientRequest CLIENT_RESQUEST = new ClientRequest("Client Name", LocalDate.of(2000, 1, 1), "12345678909");
    public static final ClientRequest INVALID_CLIENT_REQUEST = new ClientRequest(null, null, null);
    public static final Client CLIENT = new Client(1L, CLIENT_RESQUEST.name(), CLIENT_RESQUEST.birthDate(), CLIENT_RESQUEST.cpf(), LocalDateTime.now());
}
