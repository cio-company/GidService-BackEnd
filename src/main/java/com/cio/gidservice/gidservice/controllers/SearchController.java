package com.cio.gidservice.gidservice.controllers;

import com.cio.gidservice.gidservice.entities.databaseEntities.Service;
import com.cio.gidservice.gidservice.repositories.SearchServiceRepository;
import com.cio.gidservice.gidservice.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/find")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/services")
    public ResponseEntity<?> findByWords(@RequestParam List<String> keyWords){
        Set<Service> services = searchService.searchService(keyWords.toArray());
        if(services.size() != 0)
            return new ResponseEntity<>(services, HttpStatus.OK);
        else
            return new ResponseEntity<>("Services not found", HttpStatus.BAD_REQUEST);
    }
}
