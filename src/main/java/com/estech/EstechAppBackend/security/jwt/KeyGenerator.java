package com.estech.EstechAppBackend.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {

    private static final Logger logger = LoggerFactory.getLogger(KeyGenerator.class);

    /**
     * Main Function to generate a Base64 Secret Key for JWT Decoder.
     * The key will be generated and printed on the console.
     */
    public static void main(String[] args) {
        int keyLengthBytes = 32;

        byte[] keyBytes = new byte[keyLengthBytes];
        new SecureRandom().nextBytes(keyBytes);

        String base64Key = Base64.getEncoder().encodeToString(keyBytes);

        logger.info("Clave generada: " + base64Key);
    }

}
