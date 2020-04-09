package com.cio.gidservice.gidservice.entities.databaseEntities;


import com.cio.gidservice.gidservice.entities.requestEntities.LogsRequestEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 *
 */
@Data
@Entity
@Table(name="logs")
@NoArgsConstructor
@AllArgsConstructor
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Long userID;
    private String password;
    private LocalDateTime time;
    private String ip;

    public Logs(Long userID, String password, LocalDateTime localDateTime, String ip) {
        this.userID = userID;
        this.password = password;
        this.time = localDateTime;
        this.ip = ip;
    }

    public Logs(LogsRequestEntity entity) {
        setTime(LocalDateTime.now());
        setId(entity.getId());
        setIp(entity.getIp());
        setPassword(entity.getPassword());
        setUserID(entity.getUserID());
    }

}
