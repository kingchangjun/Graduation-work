package com.example.ttst.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SignupRequest { //회원가입 요청
    private String email;
    private String name;
    private String password;
    private String gender;
    private int birthYear;
    private int birthMonth;
    private int birthDay;

    public LocalDate getBirthDate() {
        return LocalDate.of(birthYear, birthMonth, birthDay);
    }

}
