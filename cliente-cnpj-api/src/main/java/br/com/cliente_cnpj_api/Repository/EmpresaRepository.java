package br.com.cliente_cnpj_api.Repository;

import br.com.cliente_cnpj_api.Model.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, String> {
}
