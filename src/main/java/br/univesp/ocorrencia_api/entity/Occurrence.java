package br.univesp.ocorrencia_api.entity;

import br.univesp.ocorrencia_api.enums.OccurrenceStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "occurrences")
public class Occurrence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_client", nullable = false)
    private Client client;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_address", nullable = false)
    private Address address;

    @Column(name = "occurrence_date", nullable = false)
    private LocalDateTime date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    @Column(name = "occurrence_status", columnDefinition = "occurrence_status", nullable = false)
    @ColumnTransformer(read = "occurrence_status::varchar", write = "?::occurrence_status")
    private OccurrenceStatus occurrenceStatus = OccurrenceStatus.ACTIVE;

    @OneToMany(mappedBy = "occurrence")
    private List<OccurrencePhoto> occurrencePhotos = new ArrayList<>();
}
