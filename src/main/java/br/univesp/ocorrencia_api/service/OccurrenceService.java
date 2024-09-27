package br.univesp.ocorrencia_api.service;

import br.univesp.ocorrencia_api.entity.Address;
import br.univesp.ocorrencia_api.entity.Client;
import br.univesp.ocorrencia_api.entity.Occurrence;
import br.univesp.ocorrencia_api.entity.OccurrencePhoto;
import br.univesp.ocorrencia_api.enums.OccurrenceStatus;
import br.univesp.ocorrencia_api.exception.CannotUpdateFinishedOccurrenceException;
import br.univesp.ocorrencia_api.exception.OccurrenceNotFoundException;
import br.univesp.ocorrencia_api.repository.OccurrenceRepository;
import br.univesp.ocorrencia_api.repository.specifications.OccurrenceSpecs;
import br.univesp.ocorrencia_api.usecases.occurrenceusecases.OccurrenceFilter;
import br.univesp.ocorrencia_api.usecases.occurrenceusecases.OccurrenceRequest;
import br.univesp.ocorrencia_api.usecases.occurrenceusecases.OccurrenceResponse;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class OccurrenceService {

    private final OccurrenceRepository occurrenceRepository;
    private final ClientService clientService;
    private final AddressService addressService;
    private final OccurrencePhotoService occurrencePhotoService;

    public static final String OCCURRENCE_NOT_FOUND = "Occurrence not found";

    public OccurrenceService(OccurrenceRepository occurrenceRepository, ClientService clientService, AddressService addressService, OccurrencePhotoService occurrencePhotoService) {
        this.occurrenceRepository = occurrenceRepository;
        this.clientService = clientService;
        this.addressService = addressService;
        this.occurrencePhotoService = occurrencePhotoService;
    }

    /**
     * Build occurrence by request
     * @param request occurrence request
     * @return occurrence
     */
    private Occurrence buildOccurrenceByRequest(OccurrenceRequest request) {
        Client client = clientService.findByOccurrenceRequest(request);
        Address address = addressService.findByOccurrenceRequest(request);
        return new Occurrence(client, address);
    }

    /**
     * Find all occurrences by filter
     * @param filter occurrence filter
     * @param pageable pageable
     * @return occurrences pageable
     */
    public Page<OccurrenceResponse> findAllByFilter(OccurrenceFilter filter, Pageable pageable) {
        Page<Occurrence> occurrencePage = occurrenceRepository.findAll(OccurrenceSpecs.occurrenceFilterSpec(filter), pageable);
        occurrencePage.forEach(occurrence ->
                occurrence.getOccurrencePhotos().forEach(occurrencePhoto ->
                        occurrencePhoto.setUrl(MinioService.getFileUrl(occurrencePhoto.getPathBucket(), occurrencePhoto.getHash()))
                )
        );
        return occurrencePage.map(OccurrenceResponse::fromEntity);
    }

    /**
     * Find occurrence by id
     * @param id occurrence id
     * @return occurrence response
     * @throws OccurrenceNotFoundException if occurrence not found
     */
    public OccurrenceResponse findById(Long id) {
        Occurrence occurrence = occurrenceRepository.findById(id)
                .orElseThrow(() -> new OccurrenceNotFoundException(OCCURRENCE_NOT_FOUND));
        return OccurrenceResponse.fromEntity(occurrence);
    }

    /**
     * Save occurrence photo
     * @param occurrence occurrence
     * @param images occurrence images
     */
    private void saveOccurrencePhoto(Occurrence occurrence, List<MultipartFile> images) {
        if (!CollectionUtils.isEmpty(images)) {
            for (MultipartFile image : images) {
                OccurrencePhoto occurrencePhoto = occurrencePhotoService.createByOccurrenceAndFile(occurrence, image);
                occurrence.getOccurrencePhotos().add(occurrencePhoto);
            }
        }
    }

    /**
     * Create occurrence
     * @param request occurrence request
     * @return occurrence
     */
    public OccurrenceResponse create(OccurrenceRequest request) {
        Occurrence occurrence = buildOccurrenceByRequest(request);
        occurrence = occurrenceRepository.save(occurrence);
        saveOccurrencePhoto(occurrence, request.images());
        return OccurrenceResponse.fromEntity(occurrence);
    }

    /**
     * Update occurrence by id
     * @param id occurrence id
     * @param request new occurrence data
     * @return occurrence
     * @throws CannotUpdateFinishedOccurrenceException if occurrence is finished
     */
    public OccurrenceResponse update(Long id, OccurrenceRequest request) {
        if (occurrenceRepository.existsByIdAndOccurrenceStatus(id, OccurrenceStatus.FINISHED)) {
            throw new CannotUpdateFinishedOccurrenceException("Cannot update a finished occurrence");
        }
        Occurrence occurrence = buildOccurrenceByRequest(request);
        occurrence.setId(id);
        occurrence = occurrenceRepository.save(occurrence);

        saveOccurrencePhoto(occurrence, request.images());
        return OccurrenceResponse.fromEntity(occurrence);
    }

    /**
     * Finish occurrence by id
     * @param id occurrence id
     * @return occurrence
     * @throws OccurrenceNotFoundException if occurrence not found
     */
    public OccurrenceResponse finishOccurrence(Long id) {
        Occurrence occurrence = occurrenceRepository.findById(id)
                .orElseThrow(() -> new OccurrenceNotFoundException(OCCURRENCE_NOT_FOUND));
        occurrence.setOccurrenceStatus(OccurrenceStatus.FINISHED);
        occurrenceRepository.save(occurrence);
        return OccurrenceResponse.fromEntity(occurrence);
    }

    /**
     * Delete occurrence by id
     * @param id occurrence id
     * @throws OccurrenceNotFoundException if occurrence not found
     */
    public void deleteOccurrence(Long id) {
        try {
            occurrenceRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new OccurrenceNotFoundException(OCCURRENCE_NOT_FOUND);
        }
    }
}
