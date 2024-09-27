package br.univesp.ocorrencia_api.service;

import br.univesp.ocorrencia_api.entity.Occurrence;
import br.univesp.ocorrencia_api.entity.OccurrencePhoto;
import br.univesp.ocorrencia_api.repository.OccurrencePhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
public class OccurrencePhotoService {

    private final OccurrencePhotoRepository occurrencePhotoRepository;

    public OccurrencePhotoService(OccurrencePhotoRepository occurrencePhotoRepository) {
        this.occurrencePhotoRepository = occurrencePhotoRepository;
    }

    /**
     * Create occurrence photo by occurrence and file
     * @param occurrence occurrence
     * @param file file
     * @return occurrence photo
     */
    public OccurrencePhoto createByOccurrenceAndFile(Occurrence occurrence, MultipartFile file) {
        String bucketName = "occurrence-photos";
        MinioService.uploadFile(bucketName, file);
        OccurrencePhoto occurrencePhoto = new OccurrencePhoto(occurrence, bucketName, Objects.requireNonNull(file.getOriginalFilename()));
        return occurrencePhotoRepository.save(occurrencePhoto);
    }
}
