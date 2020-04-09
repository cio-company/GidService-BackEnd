package com.cio.gidservice.gidservice.errors;

public class LoginException extends Exception {
    public LoginException(String message) {
        super(message);
    }

    public LoginException() {
        super("Something in login process was wrong!");
    }
}
