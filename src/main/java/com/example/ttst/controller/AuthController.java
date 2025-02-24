package com.example.ttst.controller;

import com.example.ttst.dto.LoginRequest;
import com.example.ttst.dto.MemberDto;
import com.example.ttst.dto.SignupRequest;
import com.example.ttst.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getUser(@RequestHeader("Authorization") String token) {
        String email = authService.getUser(token.replace("Bearer ", "")).getEmail();
        return ResponseEntity.ok(authService.getUser(email));
    }
}
