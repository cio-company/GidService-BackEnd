package com.cio.gidservice.gidservice.controllers;

import com.cio.gidservice.gidservice.entities.databaseEntities.Organization;
import com.cio.gidservice.gidservice.entities.databaseEntities.Service;
import com.cio.gidservice.gidservice.services.OrganizationService;
import com.cio.gidservice.gidservice.utils.PublicIDSeparator;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.*;

import com.cloudinary.*;

/**
 * REST-controller for manipulation with organizations and their services.
 * Described logic that helps User to manage his organizations and services owned by Organization.
 *
 * @author Yuriy Surzhikov
 * @version 0.1
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private static final String UPLOADED_FOLDER = "D:\\MyProjects\\";

    @Autowired
    private OrganizationService organizationService;

    private static final Map<Object, Object> CONFIG = new HashMap<>();

    static {
        CONFIG.put("cloud_name", "hmc4mhnle");
        CONFIG.put("api_key", "918324838135144");
        CONFIG.put("api_secret", "DQLwra1Rg8er_oH7Mh-zunkrJrM");
        CONFIG.put("", "");
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        List<Organization> list = organizationService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/getAllService/{user_id}")
    public ResponseEntity<?> getAllServicesForUser(@PathVariable(value = "user_id") Long user_id) {
        return new ResponseEntity<>(organizationService.findAllForUser(user_id), HttpStatus.OK);
    }


    @GetMapping("/{user_id}/get-all")
    public ResponseEntity<?> getAllForUser(@PathVariable(value = "user_id") Long user_id) {
        List<Organization> list = organizationService.findAllByUserId(user_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/{user_id}/add")
    public ResponseEntity<?> addEntity(@PathVariable Long user_id,
                                       @RequestParam("photo") MultipartFile file,
                                       @RequestParam("name") String name,
                                       @RequestParam("description") String description,
                                       @RequestParam("lat") Double lat,
                                       @RequestParam("lng") Double lng) {
        try {
            byte[] bytes = file.getBytes();
            Map uploadResult = new Cloudinary(CONFIG).uploader().upload(bytes, ObjectUtils.emptyMap());
            Organization organization = new Organization();
            organization.setName(name);
            organization.setDescription(description);
            organization.setRating(5f);
            organization.setImageUrl((String) uploadResult.get("url"));
            organization.setLat(lat);
            organization.setLng(lng);
            organizationService.addOrganization(user_id, organization);
            return ResponseEntity.ok()
                    .body(uploadResult.get("url"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getCause() + "Something went wrong! Oops:(");
        }
    }

    @PostMapping("/{id_org}/addService")
    public ResponseEntity<?> addServiceToOrganization(@PathVariable(name = "id_org") Long org_id,
                                                      @RequestParam("photo") MultipartFile file,
                                                      @RequestParam("name") String name,
                                                      @RequestParam("description") String description,
                                                      @RequestParam("cost") String cost) {
        byte[] bytes;
        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        System.out.println(cost);
        Map uploadResult;
        try {
            uploadResult = new Cloudinary(CONFIG).uploader().upload(bytes, ObjectUtils.emptyMap());
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Service service = new Service();
        service.setName(name);
        service.setDescription(description);
        service.setPrice(Float.valueOf(cost));
        service.setImageUrl((String) uploadResult.get("url"));
        Long id = organizationService.addServiceToOrganization(org_id, service);
        System.out.println(id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping("/getServices")
    public ResponseEntity<?> getServicesByOrganization(@RequestParam(value = "org_id") Long orgId) {
        try {
            return new ResponseEntity<>(organizationService.getAllServicesByOrganization(orgId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/changeOrganization/{id_org}")
    public ResponseEntity<?> updateOrganization(@PathVariable("id_org") Long orgId,
                                                @RequestParam("photo") MultipartFile file,
                                                @RequestParam("name") String name,
                                                @RequestParam("description") String description,
                                                @RequestParam("lat") Double lat,
                                                @RequestParam("lng") Double lng) {
        String prevUrl = organizationService.findOrganization(orgId).getImageUrl();
        String newUrl = null;
        if(file != null) {
            PublicIDSeparator idSeparater = new PublicIDSeparator(prevUrl, "\\S+.jpg$");
            try {
                Map result = new Cloudinary(CONFIG).uploader().destroy(idSeparater.separate(), ObjectUtils.emptyMap());
                result = new Cloudinary(CONFIG).uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                newUrl = (String) result.get("url");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            newUrl = prevUrl;
        }
        Organization organization = new Organization(orgId,
                name, 
                description,
                organizationService.findOrganization(orgId).getRating(), 
                newUrl,
                lat,
                lng,
                organizationService.findOrganization(orgId).getServices(),
                organizationService.findOrganization(orgId).getUser());
        if(organizationService.update(organization))
            return new ResponseEntity<>("Organization updated successfully!", HttpStatus.OK);
        else
            return new ResponseEntity<>("Organization cannot be updated!", HttpStatus.EXPECTATION_FAILED);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteOrganization(@RequestParam("id_org") Long orgId) {
        try{
            organizationService.deleteOrganization(orgId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
