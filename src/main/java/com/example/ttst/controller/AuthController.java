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

    // 아이디 찾기
    @GetMapping("/find-id")
    public ResponseEntity<String> findUserId(@RequestParam String email) {
        String userId = authService.findUserIdByEmail(email);
        return ResponseEntity.ok("회원님의 아이디(이메일): " + userId);
    }

    //  비밀번호 초기화
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String name,
                                                @RequestParam String email,
                                                @RequestParam String newPassword) {
        authService.resetPassword(name, email, newPassword);
        return ResponseEntity.ok("비밀번호가 재설정되었습니다.");
    }
}
