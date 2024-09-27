package br.univesp.ocorrencia_api.repository;

import br.univesp.ocorrencia_api.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByCpf(String cpf);

    Optional<Client> findByNameAndCpf(String name, String cpf);
}
