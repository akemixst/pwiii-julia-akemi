package br.com.cliente_cnpj_api.Exception;

public class EmpresaJaExisteException extends RuntimeException {
    public EmpresaJaExisteException(String message) {
        super(message);
    }
}
