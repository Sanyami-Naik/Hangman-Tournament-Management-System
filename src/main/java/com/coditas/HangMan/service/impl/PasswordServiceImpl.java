package com.coditas.HangMan.service.impl;

import com.coditas.HangMan.entity.User;
import com.coditas.HangMan.exception.ResourceNotFoundException;
import com.coditas.HangMan.payload.VerifyOtpDto;
import com.coditas.HangMan.repository.UserRepository;
import com.coditas.HangMan.service.interfaces.PasswordService;
import com.coditas.HangMan.utils.EmailSenderService;
import com.coditas.HangMan.utils.PasswordEncoderProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    NurseServiceImpl nurseService;
    @Autowired
    EmailSenderService emailSenderService;
    @Override
    public String forgotPassWord(String email) {
        User user=userRepository.findByUserName(email).orElseThrow(()->new ResourceNotFoundException("\"Please Enter Existing Mail Id\""));
        if(user!=null) {
            Random random=new Random();
            String password= String.valueOf(random.nextInt(100000)+10000);
            emailSenderService.sendSimpleEmail(email, "your OTP" + password, "Forgot PassWord OTP");
            int otp=Integer.parseInt(password);
            user.setOtp(otp);
            userRepository.save(user);
            return "OTP Is Sent To Respected Mail Id";
        }
        return "Please Enter Existing Mail Id";    }

    @Override
    public String resetPassword(String email, String newPassWord,String oldPassWord) {
        User user=userRepository.findByUserName(email).get();
        if(user!=null){

            if( new BCryptPasswordEncoder().matches(oldPassWord,user.getUserPassword())){

                user.setUserPassword(PasswordEncoderProvider.passwordEncoder(newPassWord));
                userRepository.save(user);
                System.out.println("done");
                return "Reset is done";
            }else{
                System.out.println("no");
                return "Old password doesn't match";

            }
        }

        return "Please Enter Existing Mail Id";
    }


    @Override
    public String verifyOtp(VerifyOtpDto verifyOtpDto) {

        System.out.println(verifyOtpDto.getOtp());
        User user=userRepository.findByUserName(verifyOtpDto.getEmail()).get();
        System.out.println(user);
        if (verifyOtpDto.getOtp()==user.getOtp()){
            user.setUserPassword(PasswordEncoderProvider.passwordEncoder(verifyOtpDto.getNewPassWord()));
            userRepository.save(user);
            System.out.println("done");
            return "Reset is done";
        }else {
            return "Invalid otp";
        }
    }
}
