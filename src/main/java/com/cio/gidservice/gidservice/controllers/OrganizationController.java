package com.cio.gidservice.gidservice.controllers;

import com.cio.gidservice.gidservice.entities.databaseEntities.Organization;
import com.cio.gidservice.gidservice.entities.databaseEntities.Service;
import com.cio.gidservice.gidservice.entities.requestEntities.OrganizationRequestEntity;
import com.cio.gidservice.gidservice.entities.requestEntities.ServiceRequestEntity;
import com.cio.gidservice.gidservice.errors.LoginException;
import com.cio.gidservice.gidservice.services.OrganizationService;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.cloudinary.*;

import javax.validation.Valid;

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

    @PostMapping("/image")
    public ResponseEntity<?> uploadFile(@RequestParam("photo") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Map uploadResult = new Cloudinary(CONFIG).uploader().upload(bytes, ObjectUtils.emptyMap());
            return ResponseEntity.ok()
                    .body(uploadResult.get("url"));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Something went wrong! Oops:(");
        }
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
                                                      @RequestParam("cost") Float cost) {
        try {
            byte[] bytes = file.getBytes();
            Map uploadResult = new Cloudinary(CONFIG).uploader().upload(bytes, ObjectUtils.emptyMap());
            Service service = new Service();
            service.setName(name);
            service.setDescription(name);
            service.setPrice(cost);
            service.setImageUrl((String) uploadResult.get("url"));
            Long id = organizationService.addServiceToOrganization(org_id, service);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("User or organization not found!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getServices")
    public ResponseEntity<?> getServicesByOrganization(@RequestParam(value = "org_id") Long orgId) {
        try {
            return new ResponseEntity<>(organizationService.getAllServicesByOrganization(orgId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
