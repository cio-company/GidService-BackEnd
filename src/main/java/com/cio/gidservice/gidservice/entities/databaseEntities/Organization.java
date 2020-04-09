package com.cio.gidservice.gidservice.entities.databaseEntities;

import com.cio.gidservice.gidservice.entities.requestEntities.OrganizationRequestEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


/**
 *
 */
@Entity
@Table(name="organization")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Organization {

    // Основные поля сущности Заведение
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @PositiveOrZero
    private Long id;
    @NotEmpty
    private String name;
    private String description;
    @PositiveOrZero
    private Float rating;

    /*@OneToOne(mappedBy = "organization")
    private Location location;*/

    //Связь между заведением и услугами
    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
    private List<Service> services;

    //Связь с пользователем, которому заведение принадлежит
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    public Organization(OrganizationRequestEntity entity) {
        this.description = entity.getDescription();
        this.name = entity.getName();
        this.rating = entity.getRating();
        this.services = entity.getServices();
        this.user = entity.getUser();
    }

    @Override
    public String toString() {
        return "Organization{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                //", location=" + location +
                ", services=" + services +
                ", user=" + user +
                '}';
    }

    /*
    * TODO:
    *  1. Add keywords system.
    *  2. Add photo
    *  3. Add relation with businessman
    */

}

