package br.com.cliente_cnpj_api.Controller;

import br.com.cliente_cnpj_api.Dto.EmpresaUpdateRequest;
import br.com.cliente_cnpj_api.Exception.CnpjInvalidoException;
import br.com.cliente_cnpj_api.Exception.EmpresaJaExisteException;
import br.com.cliente_cnpj_api.Exception.RecursoNaoEncontradoException;
import br.com.cliente_cnpj_api.Model.Empresa;
import br.com.cliente_cnpj_api.Service.Facade.EmpresaFacade;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmpresaController.class)
class EmpresaControllerTest {

    private static final String CNPJ = "00000000000191";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmpresaFacade facade;

    private Empresa criarEmpresa() {
        Empresa e = new Empresa();
        e.setCnpj(CNPJ);
        e.setRazaoSocial("BANCO DO BRASIL SA");
        e.setMunicipio("BRASILIA");
        e.setUf("DF");
        return e;
    }

    @Test
    void criar_deveRetornar201ComLocation() throws Exception {
        when(facade.buscarNaBrasilApiESalvar(CNPJ)).thenReturn(criarEmpresa());

        mockMvc.perform(post("/empresas/" + CNPJ))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.cnpj").value(CNPJ))
                .andExpect(jsonPath("$.municipio").value("BRASILIA"));
    }

    @Test
    void criar_jaExistente_deveRetornar409() throws Exception {
        when(facade.buscarNaBrasilApiESalvar(CNPJ))
                .thenThrow(new EmpresaJaExisteException("Empresa já cadastrada"));

        mockMvc.perform(post("/empresas/" + CNPJ))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409));
    }

    @Test
    void criar_cnpjInvalido_deveRetornar400() throws Exception {
        when(facade.buscarNaBrasilApiESalvar("123"))
                .thenThrow(new CnpjInvalidoException("CNPJ inválido"));

        mockMvc.perform(post("/empresas/123"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void buscar_existente_deveRetornar200() throws Exception {
        when(facade.buscarLocal(CNPJ)).thenReturn(criarEmpresa());

        mockMvc.perform(get("/empresas/" + CNPJ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cnpj").value(CNPJ));
    }

    @Test
    void buscar_inexistente_deveRetornar404ComCorpoDeErro() throws Exception {
        when(facade.buscarLocal(CNPJ))
                .thenThrow(new RecursoNaoEncontradoException("Nenhuma empresa cadastrada para o CNPJ " + CNPJ));

        mockMvc.perform(get("/empresas/" + CNPJ))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void listar_deveRetornar200ComPagina() throws Exception {
        Page<Empresa> pagina = new PageImpl<>(List.of(criarEmpresa()));
        when(facade.listarTodas(any())).thenReturn(pagina);

        mockMvc.perform(get("/empresas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].cnpj").value(CNPJ));
    }

    @Test
    void atualizar_comDadosInvalidos_deveRetornar400() throws Exception {
        EmpresaUpdateRequest request = new EmpresaUpdateRequest();
        // razaoSocial, municipio e uf ficam em branco de propósito

        mockMvc.perform(put("/empresas/" + CNPJ)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details").isArray());
    }

    @Test
    void atualizar_comDadosValidos_deveRetornar200() throws Exception {
        EmpresaUpdateRequest request = new EmpresaUpdateRequest();
        request.setRazaoSocial("BANCO DO BRASIL SA");
        request.setMunicipio("BRASILIA");
        request.setUf("DF");

        when(facade.atualizar(anyString(), any())).thenReturn(criarEmpresa());

        mockMvc.perform(put("/empresas/" + CNPJ)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cnpj").value(CNPJ));
    }

    @Test
    void deletar_deveRetornar204() throws Exception {
        mockMvc.perform(delete("/empresas/" + CNPJ))
                .andExpect(status().isNoContent());
    }
}
