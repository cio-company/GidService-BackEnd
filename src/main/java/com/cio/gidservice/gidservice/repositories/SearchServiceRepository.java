package com.cio.gidservice.gidservice.repositories;

import com.cio.gidservice.gidservice.entities.databaseEntities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchServiceRepository extends JpaRepository<Service, Long> {
    List<Service> findAllByDescriptionContains(String description);
}
