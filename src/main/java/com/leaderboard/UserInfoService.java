package com.leaderboard;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserInfoService implements UserDetailsService {

    private final Repository repo;
    private final PasswordEncoder encoder;
    
    public UserInfoService(Repository repo,PasswordEncoder encoder)
    {
        this.repo=repo;
        this.encoder=encoder;

    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Optional<UserInfo>userDetails=repo.findByUsername(username);
        return userDetails.map(UserInfoDetails::new).orElseThrow(
            ()->new UsernameNotFoundException("User not found "+username)
        );
    }
    public String adduser(UserInfo userInfo)
    {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repo.save(userInfo);
        return "User Added Succesfully";
    }
    
}
