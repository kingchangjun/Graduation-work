package com.example.ttst.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SignupRequest { //회원가입 요청
    private String email;
    private String name;
    private String password;

}
