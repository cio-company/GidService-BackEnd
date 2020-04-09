package com.cio.gidservice.gidservice.repositories;

import com.cio.gidservice.gidservice.entities.databaseEntities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface ServicesRepository extends JpaRepository<Service, Long> {
    List<Service> findAllByOrganizationId(Long l);
    List<Service> findServicesByDescriptionContainingOrNameContaining(String s1, String s2);
}

