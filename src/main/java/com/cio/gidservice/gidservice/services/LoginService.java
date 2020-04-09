package com.cio.gidservice.gidservice.services;

import com.cio.gidservice.gidservice.entities.databaseEntities.Logs;
import com.cio.gidservice.gidservice.entities.databaseEntities.User;
import com.cio.gidservice.gidservice.entities.requestEntities.LogsRequestEntity;
import com.cio.gidservice.gidservice.repositories.LogsRepository;
import com.cio.gidservice.gidservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;

/**
 *
 */
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogsRepository logsRepository;

    public void logOut(Long userID, String ip) {
        logsRepository.deleteAllLogsByUserIDAndIpEquals(userID, ip);
    }

    public Long trySignIn(LogsRequestEntity logs) throws IllegalAccessException {
        User user = userRepository.getOne(logs.getUserID());

        //Проверка на то, есть ли в базе сведения о входе пользователя в систему
        boolean isLoggingExists = logsRepository.existsLogsByUserIDAndIpEquals(logs.getUserID(), logs.getIp());
        if(user.getPassword().equals(logs.getPassword()) && !isLoggingExists){
            logsRepository.save(new Logs(logs));
            return user.getId();
        }
        else if(isLoggingExists) {
            return logsRepository.getByUserIDAndIp(logs.getUserID(), logs.getIp()).getId();
        }
        else {
            throw new IllegalAccessException("");
        }
    }

    public boolean userExists(User user) {
        return userRepository.existsByLogin(user.getLogin());
    }
}
