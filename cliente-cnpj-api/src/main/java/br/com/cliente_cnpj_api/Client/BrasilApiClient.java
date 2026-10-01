package br.com.cliente_cnpj_api.Client;

import br.com.cliente_cnpj_api.Dto.EmpresaResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "brasilapi", url = "https://brasilapi.com.br/api")
public interface BrasilApiClient {

    @GetMapping("/cnpj/v1/{cnpj}")
    EmpresaResponse buscarCnpj(@PathVariable("cnpj") String cnpj);
}
