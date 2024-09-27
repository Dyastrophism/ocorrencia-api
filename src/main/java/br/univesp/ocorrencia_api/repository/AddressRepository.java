package br.univesp.ocorrencia_api.repository;

import br.univesp.ocorrencia_api.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByPublicPlaceAndNeighborhoodAndZipCode(String publicPlace, String neighborhood, String zipCode);
}
