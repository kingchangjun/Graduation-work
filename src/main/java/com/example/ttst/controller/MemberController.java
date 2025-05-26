package com.example.ttst.controller;

import com.example.ttst.dto.MemberDto;
import com.example.ttst.repository.MemberRepository;
import com.example.ttst.security.CustomUserDetails;
import com.example.ttst.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.ttst.entity.Member; // Member import 추가!
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor

public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Member> getMember(@PathVariable Long id) {
        return memberRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private final MemberService memberService;

    // 회원 정보 조회
    @GetMapping
    public ResponseEntity<MemberDto> getMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MemberDto dto = memberService.getMemberDto(userDetails.getMember().getEmail());
        return ResponseEntity.ok(dto);
    }

    // 회원 정보 수정
    @PutMapping
    public ResponseEntity<String> updateMyInfo(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @RequestBody MemberDto request) {
        memberService.updateMemberInfo(userDetails.getMember().getEmail(), request);
        return ResponseEntity.ok("회원 정보가 수정되었습니다.");
    }

}
