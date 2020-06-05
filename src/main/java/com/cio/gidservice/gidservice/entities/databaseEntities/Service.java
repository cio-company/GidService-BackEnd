package com.cio.gidservice.gidservice.entities.databaseEntities;


import com.cio.gidservice.gidservice.entities.requestEntities.ServiceRequestEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.Objects;

/**
 *
 */
@Data
@Entity
@Table(name = "services")
@AllArgsConstructor
@NoArgsConstructor
public class Service {

    //Основные поля сущности Услуга
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Float price;

    //Связь с заведением
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    @JsonIgnore
    private Organization organization;

    /*
    * TODO:
    *  1. Add keywords system.
    *  2. Add photo(photos)
    */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return id.equals(service.id) &&
                name.equals(service.name) &&
                description.equals(service.description) &&
                Objects.equals(imageUrl, service.imageUrl) &&
                Objects.equals(price, service.price) &&
                Objects.equals(organization, service.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imageUrl, price, organization);
    }
}
