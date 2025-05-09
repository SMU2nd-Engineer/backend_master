package com.culturemoa.cultureMoaProject.user.controller;

import com.culturemoa.cultureMoaProject.user.dto.UserRegistrationDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @PostMapping("/registration")
    public ResponseEntity<?> userRegistrationDTO (
            @RequestBody UserRegistrationDTO pRequest) {
        System.out.println(pRequest);
        // 전달 받은
        return ResponseEntity.ok("User information registration completed ");
    }
}
