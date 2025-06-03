package com.example.ttst.dto;

import lombok.*;
import com.example.ttst.entity.Member;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MemberDto {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private LocalDate birthDate;
    private String address;

    public static MemberDto fromEntity(Member member){
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();

    }

}
