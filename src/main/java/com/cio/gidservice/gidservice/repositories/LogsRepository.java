package com.cio.gidservice.gidservice.repositories;

import com.cio.gidservice.gidservice.entities.databaseEntities.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 */
public interface LogsRepository extends JpaRepository<Logs, Long> {
    Boolean existsLogsByUserIDAndIpEquals(Long id, String s);
    void deleteAllLogsByUserIDAndIpEquals(Long aLong, String s);
    Logs getByUserIDAndIp(Long userID, String ip);
}
