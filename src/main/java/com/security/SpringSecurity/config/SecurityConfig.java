package com.security.SpringSecurity.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthFilter jwtAuthFilter;

        private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
                new User(
                        "adeshpadwal21@gmail.com",
                        "password",
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
                ),
                new User(
                        "USER1@gmail.com",
                        "password",
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER1"))
                ),
                new User(
                        "USER2@gmail.com",
                        "password",
                        Collections.singleton(new SimpleGrantedAuthority("ROLE_USER2"))
                )
        );

    //Spring will use below filterchain bean instaed of default one
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                return APPLICATION_USERS
                        .stream()
                        .filter(u -> u.getUsername().equals(email))
                        .findFirst()
                        .orElseThrow(() -> new UsernameNotFoundException("No user was found"));
            }
        };
    }

}
