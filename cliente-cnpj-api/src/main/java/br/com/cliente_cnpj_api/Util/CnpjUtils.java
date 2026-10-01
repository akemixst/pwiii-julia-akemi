package br.com.cliente_cnpj_api.Util;

import br.com.cliente_cnpj_api.Exception.CnpjInvalidoException;

import java.util.regex.Pattern;

public final class CnpjUtils {

    // Aceita 14 caracteres, com ou sem máscara (00.000.000/0000-00).
    // Os 12 primeiros podem ser letras ou números (CNPJ alfanumérico); os 2 últimos (dígitos verificadores) são numéricos.
    private static final Pattern FORMATO =
            Pattern.compile("^[A-Za-z0-9]{2}\\.?[A-Za-z0-9]{3}\\.?[A-Za-z0-9]{3}/?[A-Za-z0-9]{4}-?\\d{2}$");

    private static final int[] PESOS_DV1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] PESOS_DV2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private CnpjUtils() {
    }

    // Remove a máscara, converte para maiúsculas e valida formato e dígitos verificadores.
    // Lança CnpjInvalidoException (-> 400) em caso de CNPJ inválido.
    public static String normalizarEValidar(String cnpj) {
        if (cnpj == null || !FORMATO.matcher(cnpj).matches()) {
            throw new CnpjInvalidoException(
                    "CNPJ inválido: '" + cnpj + "'. Formato esperado: 00000000000000 ou 00.000.000/0000-00");
        }

        String limpo = cnpj.replaceAll("[./-]", "").toUpperCase();

        if (limpo.chars().distinct().count() == 1 || !digitosVerificadoresCorretos(limpo)) {
            throw new CnpjInvalidoException("CNPJ inválido: '" + cnpj + "' (dígitos verificadores incorretos)");
        }
        return limpo;
    }

    private static boolean digitosVerificadoresCorretos(String cnpj) {
        int dv1 = calcularDigito(cnpj, PESOS_DV1);
        int dv2 = calcularDigito(cnpj, PESOS_DV2);
        return dv1 == cnpj.charAt(12) - '0' && dv2 == cnpj.charAt(13) - '0';
    }

    // O valor de cada caractere é o seu código ASCII menos 48 ('0'), o que cobre dígitos e letras.
    private static int calcularDigito(String cnpj, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < pesos.length; i++) {
            soma += (cnpj.charAt(i) - '0') * pesos[i];
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }
}
