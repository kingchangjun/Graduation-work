package com.example.ttst.dto;

import lombok.*;
import com.example.ttst.entity.Member;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class MemberDto {
    private Long id;
    private String email;
    private String name;

    public static MemberDto fromEntity(Member member){
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .build();

    }
}
