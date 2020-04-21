package com.cio.gidservice.gidservice.services;

import com.cio.gidservice.gidservice.entities.databaseEntities.Logs;
import com.cio.gidservice.gidservice.entities.databaseEntities.User;
import com.cio.gidservice.gidservice.entities.requestEntities.UserRequestEntity;
import com.cio.gidservice.gidservice.errors.RegistrationException;
import com.cio.gidservice.gidservice.repositories.LogsRepository;
import com.cio.gidservice.gidservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 *
 */
@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LogsRepository logsRepository;

    public User register(User user) throws RegistrationException {
        if(userRepository.findByLogin(user.getLogin()) == null) {
            user.setPhoneNumber("+380931262912");
            return userRepository.save(user);
        }
        throw new RegistrationException();
    }
}
