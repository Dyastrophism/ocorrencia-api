package br.univesp.ocorrencia_api.controller;

import br.univesp.ocorrencia_api.service.OccurrenceService;
import br.univesp.ocorrencia_api.usecases.occurrenceusecases.OccurrenceFilter;
import br.univesp.ocorrencia_api.usecases.occurrenceusecases.OccurrenceRequest;
import br.univesp.ocorrencia_api.usecases.occurrenceusecases.OccurrenceResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/occurrences")
public class OccurrenceController {

    private final OccurrenceService occurrenceService;

    public OccurrenceController(OccurrenceService occurrenceService) {
        this.occurrenceService = occurrenceService;
    }

    @Operation(summary = "Get all occurrences")
    @GetMapping
    public Page<OccurrenceResponse> getOccurrences(@ParameterObject OccurrenceFilter occurrenceFilter, @PageableDefault(sort = {"id", "address.city"}) @ParameterObject Pageable pageable) {
        return occurrenceService.findAllByFilter(occurrenceFilter, pageable);
    }

    @Operation(summary = "Get occurrence by id")
    @GetMapping("/{id}")
    public OccurrenceResponse getOccurrence(@Parameter(description = "Occurrence id") @PathVariable("id") Long id) {
        return occurrenceService.findById(id);
    }

    @Operation(summary = "Create occurrence")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public OccurrenceResponse createOccurrence(@RequestBody OccurrenceRequest occurrenceRequest) {
        return occurrenceService.create(occurrenceRequest);
    }

    @Operation(summary = "Update occurrence")
    @PatchMapping("/{id}")
    public OccurrenceResponse updateOccurrence(@Parameter(description = "Occurrence id") @PathVariable("id") Long id, @RequestBody OccurrenceRequest occurrenceRequest) {
        return occurrenceService.update(id, occurrenceRequest);
    }

    @Operation(summary = "Finish occurrence")
    @PatchMapping("/finish/{id}")
    public OccurrenceResponse finishOccurrence(@Parameter(description = "Occurrence id") @PathVariable("id") Long id) {
        return occurrenceService.finishOccurrence(id);
    }

    @Operation(summary = "Delete occurrence")
    @DeleteMapping("/{id}")
    public void deleteOccurrence(@Parameter(description = "Occurrence id") @PathVariable("id") Long id) {
        occurrenceService.deleteOccurrence(id);
    }

}
