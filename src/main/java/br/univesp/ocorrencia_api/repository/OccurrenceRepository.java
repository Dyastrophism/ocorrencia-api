package br.univesp.ocorrencia_api.repository;

import br.univesp.ocorrencia_api.entity.Occurrence;
import br.univesp.ocorrencia_api.enums.OccurrenceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OccurrenceRepository extends JpaRepository<Occurrence, Long> , JpaSpecificationExecutor<Occurrence> {
    boolean existsByIdAndOccurrenceStatus(Long id, OccurrenceStatus occurrenceStatus);

}
