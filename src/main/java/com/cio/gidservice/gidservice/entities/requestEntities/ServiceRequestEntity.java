package com.cio.gidservice.gidservice.entities.requestEntities;

import com.cio.gidservice.gidservice.entities.databaseEntities.Service;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Data
public class ServiceRequestEntity extends Service {
    private String userIP;
    private Long organizationID;
}
