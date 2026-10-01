package br.com.cliente_cnpj_api.Service.Facade;

import br.com.cliente_cnpj_api.Client.BrasilApiClient;
import br.com.cliente_cnpj_api.Dto.EmpresaResponse;
import br.com.cliente_cnpj_api.Dto.EmpresaUpdateRequest;
import br.com.cliente_cnpj_api.Exception.BrasilApiIndisponivelException;
import br.com.cliente_cnpj_api.Exception.CnpjInvalidoException;
import br.com.cliente_cnpj_api.Exception.EmpresaJaExisteException;
import br.com.cliente_cnpj_api.Exception.RecursoNaoEncontradoException;
import br.com.cliente_cnpj_api.Model.Empresa;
import br.com.cliente_cnpj_api.Repository.EmpresaRepository;
import br.com.cliente_cnpj_api.Service.Strategy.EmpresaStrategy;
import br.com.cliente_cnpj_api.Util.CnpjUtils;
import feign.FeignException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// Facade: concentra o fluxo entre Controller, Feign Client (BrasilAPI), Strategy de
// persistência e Repository, expondo operações simples para o Controller.
@Service
public class EmpresaFacade {

    private final BrasilApiClient brasilApiClient;
    private final EmpresaStrategy strategy;
    private final EmpresaRepository repository;

    public EmpresaFacade(BrasilApiClient brasilApiClient,
                         EmpresaStrategy strategy,
                         EmpresaRepository repository) {
        this.brasilApiClient = brasilApiClient;
        this.strategy = strategy;
        this.repository = repository;
    }

    // Consulta a BrasilAPI e cria (persiste) a empresa localmente. Usado por POST.
    public Empresa buscarNaBrasilApiESalvar(String cnpjBruto) {
        String cnpj = CnpjUtils.normalizarEValidar(cnpjBruto);

        if (repository.existsById(cnpj)) {
            throw new EmpresaJaExisteException(
                    "Empresa com CNPJ " + cnpj + " já está cadastrada. Use PUT para atualizar.");
        }

        Empresa empresa = converter(cnpj, consultarBrasilApi(cnpj));
        return strategy.salvar(empresa);
    }

    // Busca uma empresa já persistida localmente. Usado por GET /{cnpj}. Operação segura/idempotente.
    public Empresa buscarLocal(String cnpjBruto) {
        String cnpj = CnpjUtils.normalizarEValidar(cnpjBruto);
        return repository.findById(cnpj)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Nenhuma empresa cadastrada para o CNPJ " + cnpj));
    }

    // Lista todas as empresas já persistidas, paginado. Usado por GET /empresas.
    public Page<Empresa> listarTodas(Pageable pageable) {
        return repository.findAll(pageable);
    }

    // Atualiza manualmente os dados de uma empresa existente. Usado por PUT /{cnpj}.
    public Empresa atualizar(String cnpjBruto, EmpresaUpdateRequest request) {
        String cnpj = CnpjUtils.normalizarEValidar(cnpjBruto);
        Empresa existente = repository.findById(cnpj)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Nenhuma empresa cadastrada para o CNPJ " + cnpj + ". Use POST para criar."));

        Empresa dadosNovos = new Empresa();
        dadosNovos.setRazaoSocial(request.getRazaoSocial());
        dadosNovos.setNomeFantasia(request.getNomeFantasia());
        dadosNovos.setSituacaoCadastral(request.getSituacaoCadastral());
        dadosNovos.setLogradouro(request.getLogradouro());
        dadosNovos.setNumero(request.getNumero());
        dadosNovos.setComplemento(request.getComplemento());
        dadosNovos.setBairro(request.getBairro());
        dadosNovos.setMunicipio(request.getMunicipio());
        dadosNovos.setUf(request.getUf() == null ? null : request.getUf().toUpperCase());
        dadosNovos.setCep(request.getCep());
        dadosNovos.setTelefone(request.getTelefone());
        dadosNovos.setEmail(request.getEmail());

        return strategy.atualizar(existente, dadosNovos);
    }

    // Remove uma empresa persistida. Usado por DELETE /{cnpj}.
    public void deletar(String cnpjBruto) {
        String cnpj = CnpjUtils.normalizarEValidar(cnpjBruto);
        if (!repository.existsById(cnpj)) {
            throw new RecursoNaoEncontradoException("Nenhuma empresa cadastrada para o CNPJ " + cnpj);
        }
        repository.deleteById(cnpj);
    }

    private EmpresaResponse consultarBrasilApi(String cnpj) {
        try {
            EmpresaResponse response = brasilApiClient.buscarCnpj(cnpj);
            if (response == null) {
                throw new RecursoNaoEncontradoException("CNPJ " + cnpj + " não encontrado na BrasilAPI");
            }
            return response;
        } catch (FeignException.NotFound ex) {
            throw new RecursoNaoEncontradoException("CNPJ " + cnpj + " não encontrado na BrasilAPI");
        } catch (FeignException.BadRequest ex) {
            throw new CnpjInvalidoException("CNPJ rejeitado pela BrasilAPI: " + cnpj);
        } catch (FeignException ex) {
            throw new BrasilApiIndisponivelException("Falha ao consultar a BrasilAPI", ex);
        }
    }

    private Empresa converter(String cnpj, EmpresaResponse response) {
        Empresa empresa = new Empresa();
        empresa.setCnpj(cnpj);
        empresa.setRazaoSocial(response.razaoSocial());
        empresa.setNomeFantasia(response.nomeFantasia());
        empresa.setSituacaoCadastral(response.situacaoCadastral());
        empresa.setLogradouro(response.logradouro());
        empresa.setNumero(response.numero());
        empresa.setComplemento(response.complemento());
        empresa.setBairro(response.bairro());
        empresa.setMunicipio(response.municipio());
        empresa.setUf(response.uf());
        empresa.setCep(response.cep());
        empresa.setTelefone(response.telefone());
        empresa.setEmail(response.email());
        return empresa;
    }
}
