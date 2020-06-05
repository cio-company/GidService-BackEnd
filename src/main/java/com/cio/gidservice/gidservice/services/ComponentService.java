package com.cio.gidservice.gidservice.services;

import com.cio.gidservice.gidservice.entities.databaseEntities.Organization;
import com.cio.gidservice.gidservice.entities.requestEntities.OrganizationRequestEntity;
import com.cio.gidservice.gidservice.repositories.OrganizationRepository;
import com.cio.gidservice.gidservice.repositories.ServicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ComponentService {

    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private OrganizationRepository organizationRepository;

    public Map<String, Double> getLatLngByService(Long serviceId) {
        Double lat = 0d;
        Double lng = 0d;
        Organization organization = organizationRepository.findOrganizationByServices(servicesRepository.getOne(serviceId));
        lat = organization.getLat();
        lng = organization.getLng();
        Double finalLat = lat;
        Double finalLng = lng;
        return new HashMap<String, Double>() {
            {
                put("lat", finalLat);
                put("lng", finalLng);
            }
        };
    }
}
