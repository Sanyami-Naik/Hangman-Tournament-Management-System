package com.coditas.HangMan.service.interfaces;

import com.coditas.HangMan.payload.VerifyOtpDto;

public interface PasswordService {
    String forgotPassWord(String email);
    String resetPassword(String email,String newPassWord,String oldPassWord);

    String verifyOtp(VerifyOtpDto verifyOtpDto);
}
