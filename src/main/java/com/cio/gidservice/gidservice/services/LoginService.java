package com.cio.gidservice.gidservice.services;

import com.cio.gidservice.gidservice.entities.databaseEntities.Logs;
import com.cio.gidservice.gidservice.entities.databaseEntities.User;
import com.cio.gidservice.gidservice.entities.requestEntities.LogsRequestEntity;
import com.cio.gidservice.gidservice.errors.LoginException;
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
        logsRepository.deleteAllLogsByUserIDEquals(userID);
    }

    public Long trySignIn(User user) throws LoginException {
        User checker = userRepository.findByLogin(user.getLogin());
        if(checker.equals(user)) {
            return checker.getId();
        } else {
            throw new LoginException("User not found!");
        }
    }

    public boolean userExists(User user) {
        return userRepository.existsByLogin(user.getLogin());
    }
}
