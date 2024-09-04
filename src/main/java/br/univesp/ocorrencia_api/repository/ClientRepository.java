package br.univesp.ocorrencia_api.repository;

import br.univesp.ocorrencia_api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByCpf(String cpf);
}
