package br.com.cliente_cnpj_api.Util;

import br.com.cliente_cnpj_api.Exception.CnpjInvalidoException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CnpjUtilsTest {

    @Test
    void cnpjValidoSemMascara() {
        assertEquals("00000000000191", CnpjUtils.normalizarEValidar("00000000000191"));
    }

    @Test
    void cnpjValidoComMascaraEhNormalizado() {
        assertEquals("11222333000181", CnpjUtils.normalizarEValidar("11.222.333/0001-81"));
    }

    @Test
    void cnpjAlfanumericoValidoEhAceito() {
        assertEquals("12ABC34501DE35", CnpjUtils.normalizarEValidar("12.abc.345/01de-35"));
    }

    @Test
    void digitoVerificadorErradoLancaExcecao() {
        assertThrows(CnpjInvalidoException.class, () -> CnpjUtils.normalizarEValidar("00000000000192"));
    }

    @Test
    void todosOsDigitosIguaisLancaExcecao() {
        assertThrows(CnpjInvalidoException.class, () -> CnpjUtils.normalizarEValidar("00000000000000"));
        assertThrows(CnpjInvalidoException.class, () -> CnpjUtils.normalizarEValidar("11111111111111"));
    }

    @Test
    void formatoInvalidoLancaExcecao() {
        assertThrows(CnpjInvalidoException.class, () -> CnpjUtils.normalizarEValidar("123"));
        assertThrows(CnpjInvalidoException.class, () -> CnpjUtils.normalizarEValidar("abc"));
        assertThrows(CnpjInvalidoException.class, () -> CnpjUtils.normalizarEValidar(null));
    }
}
