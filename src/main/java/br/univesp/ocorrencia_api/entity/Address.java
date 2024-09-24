package br.univesp.ocorrencia_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Table(name = "address")
@Data
@DynamicInsert
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "public_place", nullable = false)
    private String publicPlace;

    @Column(name = "neighborhood", nullable = false)
    private String neighborhood;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "state", nullable = false)
    private String state;

    public static Address fromRequest(String publicPlace, String neighborhood, String zipCode, String city, String state) {
        return new Address(null, publicPlace, neighborhood, zipCode, city, state);
    }

}
