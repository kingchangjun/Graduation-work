package com.example.ttst.service;

import com.example.ttst.dto.MemberDto;
import com.example.ttst.entity.Member;
import com.example.ttst.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmail(email);

        if (member.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
        }

        return User.builder()
                .username(member.get().getEmail())
                .password(member.get().getPassword())  // 비밀번호 암호화 필요
                .roles("USER")
                .build();
    }

    public MemberDto getMemberDto(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
        return MemberDto.builder()
                .email(member.getEmail())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .birthDate(member.getBirthDate())
                .address(member.getAddress())
                .build();
    }

    public void updateMemberInfo(String email, MemberDto dto) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        member.setName(dto.getName());
        member.setPhoneNumber(dto.getPhoneNumber());
        member.setBirthDate(dto.getBirthDate());
        member.setAddress(dto.getAddress());

        memberRepository.save(member);
    }

}
