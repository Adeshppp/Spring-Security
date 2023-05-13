package com.security.SpringSecurity.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class AuthenticationRequest {

    public String email;
    public String password;
}
