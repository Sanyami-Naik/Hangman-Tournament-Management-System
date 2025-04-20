package com.coditas.HangMan.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpDto {
    private String email;
    public int otp;
    private String newPassWord;
}

