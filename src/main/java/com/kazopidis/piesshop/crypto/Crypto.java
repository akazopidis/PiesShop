package com.kazopidis.piesshop.crypto;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Crypto {
    public static String hash(String password, String salt) {

        String generatedPassword = null;
        try {
            if (password != null && salt != null) {
                MessageDigest md = MessageDigest.getInstance("SHA-256");

                Base64.Decoder decoder = Base64.getDecoder();
                byte[] decodedSalt = decoder.decode(salt);
                md.update(decodedSalt);
                byte[] bytes = md.digest(password.getBytes());
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < bytes.length; i++) {
                    sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
                }
                generatedPassword = sb.toString();
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }


    public static String salt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        Base64.Encoder encoder = Base64.getEncoder();

        return encoder.encodeToString(salt);
    }
}



