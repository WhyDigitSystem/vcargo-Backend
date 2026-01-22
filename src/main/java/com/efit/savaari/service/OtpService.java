package com.efit.savaari.service;


import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.efit.savaari.entity.EmailOtpEntity;
import com.efit.savaari.repo.EmailOtpRepo;

@Service
public class OtpService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OtpService.class);  // <-- FIXED LOGGER

    private final EmailOtpRepo emailOtpRepo;
    private final EmailService emailService;
    private final AesCryptoUtil cryptoUtil;
    private final int otpLength;
    private final int expiryMinutes;

    public OtpService(EmailOtpRepo emailOtpRepo,
                      EmailService emailService,
                      @Value("${app.crypto.secret}") String secret,
                      @Value("${app.otp.length:6}") int otpLength,
                      @Value("${app.otp.expiry.minutes:5}") int expiryMinutes) {
        this.emailOtpRepo = emailOtpRepo;
        this.emailService = emailService;
        this.cryptoUtil = new AesCryptoUtil(secret);
        this.otpLength = otpLength;
        this.expiryMinutes = expiryMinutes;
    }

    // Generate numeric OTP
    private String generateOtp() {
        int max = (int) Math.pow(10, otpLength);
        int val = new Random().nextInt(max);
        return String.format("%0" + otpLength + "d", val);
    }

    public void sendOtp(String email)  {

        try {
            // 1. Generate OTP
            String otp = generateOtp();

            // 2. Encrypt OTP
            String encryptedOtp = cryptoUtil.encrypt(otp);

            // 3. Store OTP in DB
            EmailOtpEntity entity = emailOtpRepo.findByEmail(email)
                    .orElse(new EmailOtpEntity());

            entity.setEmail(email);
            entity.setEncryptedOtp(encryptedOtp);
            entity.setExpiryTime(LocalDateTime.now().plusMinutes(expiryMinutes));
//            entity.setVerified(false);
            entity.setCreatedAt(LocalDateTime.now());

            emailOtpRepo.save(entity);

            // 4. Send OTP Email
            emailService.sendOtpEmail(email, "Your verification OTP", otp);

        }
        catch (MessagingException e) {
            LOGGER.error("Email sending failed. Check SMTP / FROM address / password.", e);
            throw new RuntimeException("Unable to send OTP email");
        }
        catch (CryptoException e) {
            LOGGER.error("Encryption failed — check AES key length.", e);
            throw new RuntimeException("Internal encryption error");
        }
        catch (Exception e) {
            LOGGER.error("Unexpected error during OTP process", e);
            throw new RuntimeException("Unexpected error while generating OTP");
        }

    }


    public boolean verifyOtp(String email, String userOtp) {
        if (ObjectUtils.isEmpty(userOtp) || ObjectUtils.isEmpty(email)) return false;

        Optional<EmailOtpEntity> existing = emailOtpRepo.findByEmail(email);
        if (!existing.isPresent()) return false;

        EmailOtpEntity entity = existing.get();

        // Check expiry
        if (entity.getExpiryTime().isBefore(LocalDateTime.now())) {
            return false;
        }

        try {
            String decrypted = cryptoUtil.decrypt(entity.getEncryptedOtp());
            if (decrypted.equals(userOtp)) {
                entity.setVerified(true);
                emailOtpRepo.save(entity);
                return true;
            }
            return false;

        } catch (Exception e) {
            LOGGER.error("Error decrypting OTP", e);
            return false;
        }
    }

    public boolean isVerified(String email) {
        return emailOtpRepo.findByEmail(email)
                .map(EmailOtpEntity::isVerified)
                .orElse(false);
    }
}
