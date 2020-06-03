package com.cio.gidservice.gidservice.controllers;

import com.cio.gidservice.gidservice.services.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/components")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @GetMapping("/location")
    public ResponseEntity<?> findLocation(@RequestParam Long serviceId) {
        Map<String, Double> map = componentService.getLatLngByService(serviceId);
        if(map != null)
            return new ResponseEntity<>(map, HttpStatus.OK);
        else
            return new ResponseEntity<>("Service not found!", HttpStatus.NOT_FOUND);
    }
}
