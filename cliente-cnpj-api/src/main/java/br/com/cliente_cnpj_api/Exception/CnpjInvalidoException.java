package br.com.cliente_cnpj_api.Exception;

public class CnpjInvalidoException extends RuntimeException {
    public CnpjInvalidoException(String message) {
        super(message);
    }
}
