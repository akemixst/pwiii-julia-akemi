package br.com.cliente_cnpj_api.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmpresaUpdateRequest {

    @NotBlank(message = "razaoSocial é obrigatória")
    private String razaoSocial;

    private String nomeFantasia;

    private String situacaoCadastral;

    private String logradouro;

    private String numero;

    private String complemento;

    private String bairro;

    @NotBlank(message = "municipio é obrigatório")
    private String municipio;

    @NotBlank(message = "uf é obrigatória")
    @Size(min = 2, max = 2, message = "uf deve ter 2 caracteres")
    @Pattern(regexp = "[A-Za-z]{2}", message = "uf deve conter apenas letras")
    private String uf;

    @Pattern(regexp = "^$|\\d{8}", message = "cep deve ter 8 dígitos, sem hífen")
    private String cep;

    private String telefone;

    @Email(message = "email inválido")
    private String email;

    public String getRazaoSocial() { return razaoSocial; }
    public void setRazaoSocial(String razaoSocial) { this.razaoSocial = razaoSocial; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getSituacaoCadastral() { return situacaoCadastral; }
    public void setSituacaoCadastral(String situacaoCadastral) { this.situacaoCadastral = situacaoCadastral; }

    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getMunicipio() { return municipio; }
    public void setMunicipio(String municipio) { this.municipio = municipio; }

    public String getUf() { return uf; }
    public void setUf(String uf) { this.uf = uf; }

    public String getCep() { return cep; }
    public void setCep(String cep) { this.cep = cep; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
