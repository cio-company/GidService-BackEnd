package com.cio.gidservice.gidservice.controllers;

import com.cio.gidservice.gidservice.entities.databaseEntities.Logs;
import com.cio.gidservice.gidservice.entities.databaseEntities.User;
import com.cio.gidservice.gidservice.entities.requestEntities.LogsRequestEntity;
import com.cio.gidservice.gidservice.entities.requestEntities.UserRequestEntity;
import com.cio.gidservice.gidservice.errors.RegistrationException;
import com.cio.gidservice.gidservice.services.LoginService;
import com.cio.gidservice.gidservice.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/sign_in")
    public ResponseEntity<?> trySignIn(@RequestBody LogsRequestEntity logs) {
        try{
            return new ResponseEntity<>(loginService.trySignIn(logs), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestEntity user) {
        try {
            User aLong = registrationService.register(user);
            return new ResponseEntity<>(aLong, HttpStatus.OK);
        } catch (RegistrationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @DeleteMapping("/logout")
    public ResponseEntity<?> logoutUser(@RequestParam(value = "usr_id") Long userID,
                                        @RequestBody String ip){
        loginService.logOut(userID, ip);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
