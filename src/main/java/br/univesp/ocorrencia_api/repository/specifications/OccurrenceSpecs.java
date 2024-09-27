package br.univesp.ocorrencia_api.repository.specifications;

import br.univesp.ocorrencia_api.entity.Occurrence;
import br.univesp.ocorrencia_api.usecases.occurrenceusecases.OccurrenceFilter;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class OccurrenceSpecs {

    private OccurrenceSpecs() {
    }

    public static Specification<Occurrence> occurrenceFilterSpec(OccurrenceFilter filter) {
        return (root, query, criteriaBuilder) -> {
            final String CLIENT = "client";
            final String ADDRESS = "address";

            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.isNotEmpty(filter.clientName())) {
                predicates.add(criteriaBuilder.like(root.get(CLIENT).get("name"), "%" + filter.clientName() + "%"));
            }
            if (StringUtils.isNotEmpty(filter.clientCpf())) {
                predicates.add(criteriaBuilder.equal(root.get(CLIENT).get("cpf"), filter.clientCpf()));
            }
            if (filter.occurrenceDate() != null) {
                predicates.add(criteriaBuilder.equal(root.get("date"), filter.occurrenceDate()));
            }
            if (StringUtils.isNotEmpty(filter.cityName())) {
                predicates.add(criteriaBuilder.like(root.get(ADDRESS).get("city"), "%" + filter.cityName() + "%"));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
