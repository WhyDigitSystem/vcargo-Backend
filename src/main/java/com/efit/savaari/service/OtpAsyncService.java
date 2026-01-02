package com.efit.savaari.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class OtpAsyncService {

    private final OtpService otpService;

    public OtpAsyncService(OtpService otpService) {
        this.otpService = otpService;
    }

    @Async
    public void sendOtpAsync(String email) {
        otpService.sendOtp(email);   // Now async works!
    }
}