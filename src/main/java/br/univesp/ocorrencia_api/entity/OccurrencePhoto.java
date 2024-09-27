package br.univesp.ocorrencia_api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@DynamicInsert
@Entity
@Table(name = "occurrence_photos")
public class OccurrencePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_occurrence", nullable = false)
    private Occurrence occurrence;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @NonNull
    @Column(name = "path_bucket", nullable = false)
    private String pathBucket;

    @NonNull
    @Column(name = "hash", nullable = false)
    private String hash;

    @Transient
    private String url;

    public String getPhoto() {
        return this.url;
    }
}
