package br.univesp.ocorrencia_api.repository;

import br.univesp.ocorrencia_api.entity.OccurrencePhoto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OccurrencePhotoRepository extends JpaRepository<OccurrencePhoto, Long> {
    Page<OccurrencePhoto> findByOccurrence_Id(Long occurrenceId, Pageable pageable);
}
