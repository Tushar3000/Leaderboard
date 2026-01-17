package com.leaderboard.board.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.leaderboard.AuthRequest;
import com.leaderboard.JwtService;
import com.leaderboard.UserInfo;
import com.leaderboard.UserInfoService;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RestController

public class userController {

    private final AuthenticationManager authenticationManager;
  
    private final JwtService jwtService;
    private final UserInfoService userInfoService;

    public userController(AuthenticationManager authenticationManager,JwtService jwtService,UserInfoService userInfoService)
    {
        this.authenticationManager=authenticationManager;
        this.jwtService=jwtService;
        this.userInfoService=userInfoService;
    }

  
    @PostMapping("/login")
    public ResponseEntity<String> authenticationAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if(authentication.isAuthenticated())
            {
                String token=jwtService.generateToken(authRequest.getUsername());
                return ResponseEntity.ok(token);
                
            }
            throw new UsernameNotFoundException("Not found username");
        
        
    }
    @PostMapping("/register")
    
        //TODO: process POST request
        public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo)
        {
            String response=userInfoService.adduser(userInfo);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        
        
    
    
    

}
