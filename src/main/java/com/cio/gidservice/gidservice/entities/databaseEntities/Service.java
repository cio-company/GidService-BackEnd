package com.cio.gidservice.gidservice.entities.databaseEntities;


import com.cio.gidservice.gidservice.entities.requestEntities.ServiceRequestEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
    private Integer leadTime; //minutes
    private Float price;

    //Связь с заведением
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "organization_id", nullable = false)
    @JsonIgnore
    private Organization organization;

    public Service(ServiceRequestEntity entity) {
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.leadTime = entity.getLeadTime();
        this.organization = entity.getOrganization();
        this.price = entity.getPrice();
    }

    /*
    * TODO:
    *  1. Add keywords system.
    *  2. Add photo(photos)
    */

}
