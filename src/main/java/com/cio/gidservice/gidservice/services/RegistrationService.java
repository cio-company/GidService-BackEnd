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

    public User register(UserRequestEntity user) throws RegistrationException {
        if(!userRepository.existsUserByPhoneNumber(user.getPhoneNumber())) {
            User byLogin = userRepository.save(new User(user));
            Logs logs = new Logs(byLogin.getId(), user.getPassword(), LocalDateTime.now(), user.getIp());
            System.out.println(logs);
            logsRepository.save(logs);
            return byLogin;
        }
        throw new RegistrationException();
    }
}
