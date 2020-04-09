package com.cio.gidservice.gidservice.repositories;

import com.cio.gidservice.gidservice.entities.databaseEntities.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 */
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findOrganizationsByUserId(Long l);
    Organization findOrganizationByName(String s);
    Organization findOrganizationByDescriptionContains(String s);
}
