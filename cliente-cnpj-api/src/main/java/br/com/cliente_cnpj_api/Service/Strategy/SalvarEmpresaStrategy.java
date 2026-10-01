package br.com.cliente_cnpj_api.Service.Strategy;

import br.com.cliente_cnpj_api.Model.Empresa;
import br.com.cliente_cnpj_api.Repository.EmpresaRepository;
import org.springframework.stereotype.Service;

@Service
public class SalvarEmpresaStrategy implements EmpresaStrategy {

    private final EmpresaRepository repository;

    public SalvarEmpresaStrategy(EmpresaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Empresa salvar(Empresa empresa) {
        return repository.save(empresa);
    }

    @Override
    public Empresa atualizar(Empresa existente, Empresa dadosNovos) {
        existente.setRazaoSocial(dadosNovos.getRazaoSocial());
        existente.setNomeFantasia(dadosNovos.getNomeFantasia());
        existente.setSituacaoCadastral(dadosNovos.getSituacaoCadastral());
        existente.setLogradouro(dadosNovos.getLogradouro());
        existente.setNumero(dadosNovos.getNumero());
        existente.setComplemento(dadosNovos.getComplemento());
        existente.setBairro(dadosNovos.getBairro());
        existente.setMunicipio(dadosNovos.getMunicipio());
        existente.setUf(dadosNovos.getUf());
        existente.setCep(dadosNovos.getCep());
        existente.setTelefone(dadosNovos.getTelefone());
        existente.setEmail(dadosNovos.getEmail());
        return repository.save(existente);
    }
}
