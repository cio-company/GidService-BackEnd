package com.cio.gidservice.gidservice.entities.requestEntities;

import com.cio.gidservice.gidservice.entities.databaseEntities.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationRequestEntity extends Organization {

    private String ip;

    @Override
    public String toString() {
        return super.toString() + ip;
    }
}
