package br.univesp.ocorrencia_api.usecases.occurrenceusecases;

import br.univesp.ocorrencia_api.entity.Occurrence;
import br.univesp.ocorrencia_api.entity.OccurrencePhoto;

import java.util.List;

public record OccurrenceResponse(
        String clientName,
        String clientCpf,
        String publicPlace,
        String neighborhood,
        String zipCode,
        String city,
        String state,
        List<String> images
) {

    public static OccurrenceResponse fromEntity(Occurrence occurrence) {
        return new OccurrenceResponse(
                occurrence.getClient().getName(),
                occurrence.getClient().getCpf(),
                occurrence.getAddress().getPublicPlace(),
                occurrence.getAddress().getNeighborhood(),
                occurrence.getAddress().getZipCode(),
                occurrence.getAddress().getCity(),
                occurrence.getAddress().getState(),
                List.of(occurrence.getOccurrencePhotos().stream().map(OccurrencePhoto::getPhoto).toArray(String[]::new))
        );
    }
}
