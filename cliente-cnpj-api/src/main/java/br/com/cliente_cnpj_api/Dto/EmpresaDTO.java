package br.com.cliente_cnpj_api.Dto;

import br.com.cliente_cnpj_api.Model.Empresa;

import java.time.LocalDateTime;

public record EmpresaDTO(
        String cnpj,
        String razaoSocial,
        String nomeFantasia,
        String situacaoCadastral,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String municipio,
        String uf,
        String cep,
        String telefone,
        String email,
        LocalDateTime criadoEm,
        LocalDateTime atualizadoEm
) {
    public static EmpresaDTO fromEntity(Empresa e) {
        return new EmpresaDTO(
                e.getCnpj(), e.getRazaoSocial(), e.getNomeFantasia(), e.getSituacaoCadastral(),
                e.getLogradouro(), e.getNumero(), e.getComplemento(), e.getBairro(),
                e.getMunicipio(), e.getUf(), e.getCep(), e.getTelefone(), e.getEmail(),
                e.getCriadoEm(), e.getAtualizadoEm());
    }
}
