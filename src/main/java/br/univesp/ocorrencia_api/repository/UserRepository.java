package br.univesp.ocorrencia_api.repository;

import br.univesp.ocorrencia_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String login);

    boolean existsByUsername(String login);
}
