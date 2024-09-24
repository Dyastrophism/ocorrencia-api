package br.univesp.ocorrencia_api.repository;

import br.univesp.ocorrencia_api.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
