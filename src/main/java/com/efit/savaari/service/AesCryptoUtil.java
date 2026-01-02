package com.efit.savaari.service;

import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesCryptoUtil {

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int IV_SIZE = 16;

    private final byte[] key;

    public AesCryptoUtil(String secretKey) {
        // secretKey must be 16/24/32 bytes; use appropriate checks in production
        this.key = secretKey.getBytes();
    }

    public String encrypt(String plain) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        byte[] iv = new byte[IV_SIZE];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(iv);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivSpec);
        byte[] encrypted = cipher.doFinal(plain.getBytes("UTF-8"));

        // store iv + ":" + ciphertext (both base64)
        String ivB64 = Base64.getEncoder().encodeToString(iv);
        String encB64 = Base64.getEncoder().encodeToString(encrypted);
        return ivB64 + ":" + encB64;
    }

    public String decrypt(String ivAndCipher) throws Exception {
        String[] parts = ivAndCipher.split(":");
        if (parts.length != 2) throw new IllegalArgumentException("Invalid data");
        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] cipherBytes = Base64.getDecoder().decode(parts[1]);

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivSpec);
        byte[] original = cipher.doFinal(cipherBytes);
        return new String(original, "UTF-8");
    }
}
