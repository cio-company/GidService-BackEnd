package com.cio.gidservice.gidservice.controllers;

import com.cio.gidservice.gidservice.entities.databaseEntities.Organization;
import com.cio.gidservice.gidservice.entities.requestEntities.OrganizationRequestEntity;
import com.cio.gidservice.gidservice.entities.requestEntities.ServiceRequestEntity;
import com.cio.gidservice.gidservice.errors.LoginException;
import com.cio.gidservice.gidservice.errors.NonAuthorizedAccess;
import com.cio.gidservice.gidservice.services.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;

/**
 * REST-controller for manipulation with organizations and their services.
 * Described logic that helps User to manage his organizations and services owned by Organization.
 * @author Yuriy Surzhikov
 * @version 0.1
 */
@RestController
@RequestMapping("/organization")
public class OrganizationController {

    private static final String UPLOADED_FOLDER = "D:\\MyProjects";

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        List<Organization> list = organizationService.getAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/image")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) {
        try{
            byte[] bytes = file.getBytes();
            DateTimeFormatterBuilder.getLocalizedDateTimePattern(FormatStyle.MEDIUM, FormatStyle.MEDIUM, Chronology.ofLocale(Locale.UK), Locale.UK);
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            System.out.println(path);
            return ResponseEntity.ok()
                    .body("Your image was save! Congratulation!");
        }catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Something went wrong! Oops:(");
        }
    }


    @GetMapping("/{user_id}/get-all")
    public ResponseEntity<?> getAllForUser(@PathVariable(value = "user_id") Long user_id){
        List<Organization> list = organizationService.findAllByUserId(user_id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/{user_id}/add")
    public ResponseEntity<?> addEntity(@PathVariable Long user_id,
                                       @RequestBody OrganizationRequestEntity organization) {
        try {
            organizationService.addOrganization(user_id, organization);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (LoginException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/{user_id}/addService")
    public ResponseEntity<?> addServiceToOrganization(@PathVariable Long user_id,
                                                      @RequestBody ServiceRequestEntity service) {
        try{
            Long id = organizationService.addServiceToOrganization(service);
            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("User or organization not found!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{user_id}/getServices")
    public ResponseEntity<?> getServicesByOrganization(@PathVariable String user_id,
                                                       @RequestParam(value = "org_id") Long orgId) {
        try{
            return new ResponseEntity<>(organizationService.getAllServicesByOrganization(orgId), HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
