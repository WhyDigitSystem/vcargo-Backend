package com.efit.savaari.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.efit.savaari.responseDTO.ResponseDTO;
import com.efit.savaari.service.OtpService;

@RestController
@RequestMapping("/email")
public class OtpController {

	public static final Logger LOGGER = LoggerFactory.getLogger(OtpController.class);

    private final OtpService otpService;

    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }



//    @PostMapping("/verify-otp")
//    public ResponseEntity<ResponseDTO> verifyOtp(
//            @RequestParam String email,
//            @RequestParam String otp) {
//
//        ResponseDTO response = new ResponseDTO();
//
//        boolean ok = otpService.verifyOtp(email, otp);
//
//        if (ok) {
//            response.setStatusFlag(ResponseDTO.OK);
//            response.setStatus(true);
//            response.addObject1("message","OTP verified successfully.");
//            response.addObject1("email", email);
//            response.addObject1("verified",true);
//
//            return ResponseEntity.ok(response);
//        } 
//        else {
//            response.setStatusFlag(ResponseDTO.ERROR);
//            response.setStatus(false);
//            response.addObject1("message","Invalid or expired OTP.");
//            response.addObject1("email",email);
//            response.addObject1("verified",false);
//
//            return ResponseEntity.status(400).body(response);
//        }
//    }
    
    
    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseDTO> verifyOtp(
            @RequestParam String email,
            @RequestParam String otp) {

        try {
            ResponseDTO res = new ResponseDTO();

            boolean ok = otpService.verifyOtp(email, otp);

            if (!ok) {
                res.setStatusFlag(ResponseDTO.ERROR);
                res.setStatus(false);
                res.addObject1("message", "Invalid or expired OTP.");
                res.addObject1("email", email);
                res.addObject1("verified", false);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(res);
            }

            res.setStatusFlag(ResponseDTO.OK);
            res.setStatus(true);
            res.addObject1("message", "OTP verified successfully.");
            res.addObject1("email", email);
            res.addObject1("verified", true);

            return ResponseEntity.ok(res);

        } catch (Exception e) {

            ResponseDTO error = new ResponseDTO();
            error.setStatus(false);
            error.setStatusFlag(ResponseDTO.ERROR);
            error.addObject1("message", "Unexpected error: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }


}