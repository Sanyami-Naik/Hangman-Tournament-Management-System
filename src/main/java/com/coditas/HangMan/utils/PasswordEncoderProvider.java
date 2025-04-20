package com.coditas.HangMan.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderProvider {
    public static String passwordEncoder(String password){
        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
        String pass=passwordEncoder.encode(password);
        return pass;
    }
//    public static void main(String[] args) {
//        PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
//        System.out.println(passwordEncoder.encode("admin"));
//    }
}
