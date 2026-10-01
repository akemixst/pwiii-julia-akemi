package br.com.cliente_cnpj_api.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// Mapeia apenas os campos de interesse da resposta da BrasilAPI (que usa snake_case).
// Os demais campos (sócios, CNAEs secundários etc.) são ignorados.
@JsonIgnoreProperties(ignoreUnknown = true)
public record EmpresaResponse(
        String cnpj,
        @JsonProperty("razao_social") String razaoSocial,
        @JsonProperty("nome_fantasia") String nomeFantasia,
        @JsonProperty("descricao_situacao_cadastral") String situacaoCadastral,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String municipio,
        String uf,
        String cep,
        @JsonProperty("ddd_telefone_1") String telefone,
        String email
) {
}
