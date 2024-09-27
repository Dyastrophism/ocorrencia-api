package br.univesp.ocorrencia_api.exception;

public class OccurrenceNotFoundException extends RuntimeException {
    public OccurrenceNotFoundException(String message) {
        super(message);
    }
}
