package com.cio.gidservice.gidservice.controllers;


import com.cio.gidservice.gidservice.entities.databaseEntities.Organization;
import com.cio.gidservice.gidservice.entities.databaseEntities.Service;
import com.cio.gidservice.gidservice.services.OrganizationService;
import com.cio.gidservice.gidservice.utils.PublicIDSeparator;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/delete")
public class DeleteController {

    @Autowired
    private OrganizationService organizationService;

    private static final Map<Object, Object> CONFIG = new HashMap<>();

    static {
        CONFIG.put("cloud_name", "hmc4mhnle");
        CONFIG.put("api_key", "918324838135144");
        CONFIG.put("api_secret", "DQLwra1Rg8er_oH7Mh-zunkrJrM");
        CONFIG.put("", "");
    }

    @DeleteMapping("/organization")
    public ResponseEntity<?> deleteOrganization(@RequestParam Long id) {
        try{
            Organization organization = organizationService.findOrganization(id);
            List<Service> services = organizationService.findServicesOrganization(id);
            services.parallelStream()
                    .forEach(service -> {
                        PublicIDSeparator idSeparator = new PublicIDSeparator(service.getImageUrl(),"\\S+.jpg$");
                        try {
                            new Cloudinary(CONFIG).uploader().destroy(idSeparator.separate(), ObjectUtils.emptyMap());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            PublicIDSeparator idSeparator = new PublicIDSeparator(organization.getImageUrl(),"\\S+.jpg$");
            new Cloudinary(CONFIG).uploader().destroy(idSeparator.separate(), ObjectUtils.emptyMap());
            organizationService.deleteOrganization(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/service")
    public ResponseEntity<?> deleteService(@RequestParam Long id){
        Service service = organizationService.findService(id);
        try {
            new Cloudinary(CONFIG).uploader().destroy(new PublicIDSeparator(service.getImageUrl(),"\\S+.jpg$").separate(), ObjectUtils.emptyMap());
            organizationService.deleteService(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
