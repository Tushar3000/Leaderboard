package com.leaderboard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    private final JwtAuthFilter authFilter;

    public SecurityConfig(JwtAuthFilter authFilter)
    {
        this.authFilter=authFilter;
    }
    @Bean
    public UserDetailsService userDetailsService(Repository repo,PasswordEncoder passwordEncoder)
    {
        return new UserInfoService(repo, passwordEncoder);
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,AuthenticationProvider authenticationProvider) throws Exception
    {
        return http
        .authorizeHttpRequests((auth)->auth
    .requestMatchers("/login","/register").permitAll()
)
.httpBasic(withDefaults()).csrf((csrf)->csrf.disable())
.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
.authenticationProvider(authenticationProvider)
.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
.build();
    }
    @Bean
   AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,PasswordEncoder passwordEncoder)
    {
        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder);
        return authenticationProvider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {

        return config.getAuthenticationManager();
    }

}
