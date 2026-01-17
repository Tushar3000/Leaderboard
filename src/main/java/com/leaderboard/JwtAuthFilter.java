package com.leaderboard;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    
    private final JwtService jwtService;
    private final UserInfoService userDetailService;
    
    JwtAuthFilter(JwtService jwtService,UserInfoService userDetailsService)
    {
        this.jwtService=jwtService;
        this.userDetailService=userDetailsService;
    }
   
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        
       
        String authHeader= request.getHeader("Authorization");
        String username=null;
        String token=null;
        if(authHeader!=null && authHeader.startsWith("Bearer ") )
            {
                token=authHeader.substring(7);
                username = jwtService.extractUsername(token);
            }
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
                {
                    UserDetails userDetails=userDetailService.loadUserByUsername(username);
                    if(jwtService.validateToken(token, userDetails))
                        {
                            
                            UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authToken);

                        }
                }
                filterChain.doFilter(request, response);
            }
    
    
}
