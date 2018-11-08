package com.itsm.frontend.session;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptor {
    private String salt = "ERTYHGVBHJKMNBGFVBHJKLKMNHGTFVBHJKMOLKIUYGFDERFGVBHUJNHGTRFVGH";
    public String encrypt(String pswd){

        try {
            MessageDigest instance = MessageDigest.getInstance("SHA-256");
            instance.update(pswd.concat(salt).getBytes());
            return new String(instance.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
