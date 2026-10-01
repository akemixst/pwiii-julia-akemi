package br.com.cliente_cnpj_api.Service.Strategy;

import br.com.cliente_cnpj_api.Model.Empresa;

// Define o contrato de persistência de uma Empresa, permitindo trocar a forma
// como ela é salva/atualizada sem alterar o Facade (padrão Strategy).
public interface EmpresaStrategy {
    Empresa salvar(Empresa empresa);
    Empresa atualizar(Empresa existente, Empresa dadosNovos);
}
