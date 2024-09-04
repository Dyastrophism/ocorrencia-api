package br.univesp.ocorrencia_api.exception;

public class DuplicatedCpfException extends RuntimeException {
    public DuplicatedCpfException(String message) {
        super(message);
    }
}
