package com.example.ttst.service;

import com.example.ttst.dto.LoginRequest;
import com.example.ttst.dto.MemberDto;
import com.example.ttst.dto.SignupRequest;
import com.example.ttst.entity.Member;
import com.example.ttst.repository.MemberRepository;
import com.example.ttst.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 회원가입
   /* public String signup(SignupRequest request) {
        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .build();

        memberRepository.save(member);
        return "회원가입 성공!";
    }*/
    public String signup(SignupRequest request) {
        System.out.println("회원가입 요청 받음: " + request.getEmail());

        if (memberRepository.findByEmail(request.getEmail()).isPresent()) {
            System.out.println("이미 존재하는 이메일입니다.");
            throw new RuntimeException("이미 존재하는 이메일입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        Member member = Member.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(encodedPassword)
                .build();

        memberRepository.save(member);
        System.out.println("회원가입 성공!");
        return "회원가입 성공!";
    }

    // 로그인
    public String login(LoginRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.generateToken(member.getEmail());
    }

    // 사용자 정보 조회
    public MemberDto getUser(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        return MemberDto.fromEntity(member);
    }
}
