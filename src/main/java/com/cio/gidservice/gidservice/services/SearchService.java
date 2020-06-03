package com.cio.gidservice.gidservice.services;

import com.cio.gidservice.gidservice.repositories.SearchServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SearchService {

    @Autowired
    private SearchServiceRepository searchServiceRepository;

    public Set<com.cio.gidservice.gidservice.entities.databaseEntities.Service> searchService(Object[] keyWords) {
        HashSet<com.cio.gidservice.gidservice.entities.databaseEntities.Service> services = new HashSet<>();
        for (Object word: keyWords) {
            List<com.cio.gidservice.gidservice.entities.databaseEntities.Service> service = searchServiceRepository.findAllByDescriptionContains((String)word);
            if(service != null)
                services.addAll(service);
        }
        return services;
    }
}
