package br.univesp.ocorrencia_api.exception;

public class CannotUpdateFinishedOccurrenceException extends RuntimeException {
    public CannotUpdateFinishedOccurrenceException(String message) {
        super(message);
    }
}
