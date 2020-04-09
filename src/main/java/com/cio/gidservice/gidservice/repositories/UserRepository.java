package com.cio.gidservice.gidservice.repositories;

import com.cio.gidservice.gidservice.entities.databaseEntities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByPhoneNumber(String s);
    User findByLogin(String s);
    Boolean existsByLogin(String s);
    Boolean existsUserByPhoneNumber(String s);
}
