package com.coditas.HangMan.controller;
import com.coditas.HangMan.payload.*;
import com.coditas.HangMan.repository.RoleRepository;
import com.coditas.HangMan.repository.UserRepository;
import com.coditas.HangMan.security.JwtTokenProvider;
import com.coditas.HangMan.service.interfaces.PasswordService;
import com.coditas.HangMan.service.interfaces.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@CrossOrigin

public class AuthenticationController{

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private PasswordService passwordService;


    @PostMapping("/signin")
    public ResponseEntity<JwtAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUserName(),loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //get toke from token provider
        String token=jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtAuthResponse(token,jwtTokenProvider.getRole(jwtTokenProvider.getUsername(token)), jwtTokenProvider.getUserId(jwtTokenProvider.getUsername(token))));
    }
    @PostMapping("/forgot")
    public ResponseEntity<String> forgetPassWord(@RequestBody ForgetDto forgetDto){
        return  new ResponseEntity<>(passwordService.forgotPassWord(forgetDto.getUserName()), HttpStatus.OK);
    }
    @PostMapping("/reset")
    public ResponseEntity<String> resetPassWord(@RequestBody ResetDto resetDto){
        return  new ResponseEntity<>(passwordService.resetPassword(resetDto.getUserName(),resetDto.getNewPassword(),resetDto.getOldPassword()),HttpStatus.OK);
    }
    @PostMapping("/signUp")
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDto){
        PlayerDto playerDto1=playerService.createPlayer(playerDto);
        return new ResponseEntity<>(playerDto1,HttpStatus.CREATED);
    }

    @PostMapping("/verifyOtp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpDto verifyOtpDto){
        return  new ResponseEntity<>(passwordService.verifyOtp(verifyOtpDto), HttpStatus.OK);
    }

}
