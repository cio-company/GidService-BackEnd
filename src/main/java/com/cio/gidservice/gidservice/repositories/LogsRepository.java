package com.cio.gidservice.gidservice.repositories;

import com.cio.gidservice.gidservice.entities.databaseEntities.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface LogsRepository extends JpaRepository<Logs, Long> {
    Boolean existsLogsByUserIDEquals(Long id);
    void deleteAllLogsByUserIDEquals(Long aLong);
    Logs getByUserID(Long userID);
}
