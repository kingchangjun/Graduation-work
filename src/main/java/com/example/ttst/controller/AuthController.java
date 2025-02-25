package com.example.ttst.controller;


import com.example.ttst.dto.AuthResponse;
import com.example.ttst.dto.LoginRequest;
import com.example.ttst.dto.MemberDto;
import com.example.ttst.dto.SignupRequest;
import com.example.ttst.security.MemberDetails;
import com.example.ttst.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest request) {
        authService.signup(request);
        return ResponseEntity.ok("회원가입 성공!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @GetMapping("/me")
    public ResponseEntity<MemberDto> getUser(@AuthenticationPrincipal MemberDetails memberDetails) {
        return ResponseEntity.ok(MemberDto.fromEntity(memberDetails.getMember()));
    }
}
