package com.cio.gidservice.gidservice.services;

import com.cio.gidservice.gidservice.entities.databaseEntities.Organization;
import com.cio.gidservice.gidservice.entities.databaseEntities.User;
import com.cio.gidservice.gidservice.entities.requestEntities.OrganizationRequestEntity;
import com.cio.gidservice.gidservice.entities.requestEntities.ServiceRequestEntity;
import com.cio.gidservice.gidservice.errors.LoginException;
import com.cio.gidservice.gidservice.repositories.LogsRepository;
import com.cio.gidservice.gidservice.repositories.OrganizationRepository;
import com.cio.gidservice.gidservice.repositories.ServicesRepository;
import com.cio.gidservice.gidservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private LogsRepository logsRepository;
    @Autowired
    private UserRepository userRepository;


    public List<Organization> findAllByUserId(Long id) {
        return organizationRepository.findOrganizationsByUserId(id);
    }

    public Organization findOrganization(String name) {
        return organizationRepository.findOrganizationByName(name);
    }
    public Organization findOrganization(Long id) {
        return organizationRepository.getOne(id);
    }

    public Organization findOrganizationByKeywords(String keyword) {
        return organizationRepository.findOrganizationByDescriptionContains(keyword);
    }

    public Long addServiceToOrganization(Long orgId, com.cio.gidservice.gidservice.entities.databaseEntities.Service service) {
        Organization organization = organizationRepository.getOne(orgId);
        if(organization == null)
            throw new IllegalArgumentException(String.valueOf(orgId));
        service.setOrganization(organization);
        return servicesRepository.save(service).getId();
    }

    /**
     * Метод добавляет организацию в БД. Сперва идет поиск User для которого добавляется организация.
     * После этого найденый пользователь присваивается организации, и она идет в БД.
     * @param user_id - id пользователя для которого добавляется организация
     * @param organization - объект организации, которая добавляется для определенного клиента
     */
    public void addOrganization(Long user_id, Organization organization) {
        User user = userRepository.getOne(user_id);
        organization.setUser(user);
        user.addOrganization(organization);
        organizationRepository.save(organization);
    }

    public List<com.cio.gidservice.gidservice.entities.databaseEntities.Service> getAllServicesByOrganization(Long id) {
        return servicesRepository.findAllByOrganizationId(id);
    }

    public List<Organization> getAll() {
        return organizationRepository.findAll();
    }

    public boolean update(Organization organization) {
        if(organizationRepository.save(organization) != null)
            return true;
        else
            return false;
    }

    public void deleteOrganization(Long id) {
        organizationRepository.deleteById(id);
    }

    public void deleteService(Long id) {
        servicesRepository.deleteById(id);
    }
}
