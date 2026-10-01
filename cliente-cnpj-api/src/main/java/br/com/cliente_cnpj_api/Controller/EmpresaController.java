package br.com.cliente_cnpj_api.Controller;

import br.com.cliente_cnpj_api.Dto.EmpresaDTO;
import br.com.cliente_cnpj_api.Dto.EmpresaUpdateRequest;
import br.com.cliente_cnpj_api.Model.Empresa;
import br.com.cliente_cnpj_api.Service.Facade.EmpresaFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/empresas")
@Tag(name = "Empresas", description = "Busca, cadastro e gerenciamento de empresas via CNPJ (BrasilAPI)")
public class EmpresaController {

    private final EmpresaFacade facade;

    public EmpresaController(EmpresaFacade facade) {
        this.facade = facade;
    }

    @Operation(summary = "Consulta a BrasilAPI e cadastra a empresa localmente")
    @PostMapping("/{cnpj}")
    public ResponseEntity<EmpresaDTO> criar(@PathVariable String cnpj,
                                             UriComponentsBuilder uriBuilder) {
        Empresa empresa = facade.buscarNaBrasilApiESalvar(cnpj);
        var location = uriBuilder.path("/empresas/{cnpj}").buildAndExpand(empresa.getCnpj()).toUri();
        return ResponseEntity.created(location).body(EmpresaDTO.fromEntity(empresa));
    }

    @Operation(summary = "Busca uma empresa já cadastrada pelo CNPJ")
    @GetMapping("/{cnpj}")
    public ResponseEntity<EmpresaDTO> buscar(@PathVariable String cnpj) {
        Empresa empresa = facade.buscarLocal(cnpj);
        return ResponseEntity.ok(EmpresaDTO.fromEntity(empresa));
    }

    @Operation(summary = "Lista todas as empresas já cadastradas, paginado")
    @GetMapping
    public ResponseEntity<Page<EmpresaDTO>> listar(
            @PageableDefault(size = 20, sort = "cnpj") Pageable pageable) {
        Page<EmpresaDTO> pagina = facade.listarTodas(pageable).map(EmpresaDTO::fromEntity);
        return ResponseEntity.ok(pagina);
    }

    @Operation(summary = "Atualiza manualmente os dados de uma empresa já cadastrada")
    @PutMapping("/{cnpj}")
    public ResponseEntity<EmpresaDTO> atualizar(@PathVariable String cnpj,
                                                 @Valid @RequestBody EmpresaUpdateRequest request) {
        Empresa empresa = facade.atualizar(cnpj, request);
        return ResponseEntity.ok(EmpresaDTO.fromEntity(empresa));
    }

    @Operation(summary = "Remove uma empresa cadastrada")
    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deletar(@PathVariable String cnpj) {
        facade.deletar(cnpj);
        return ResponseEntity.noContent().build();
    }
}
