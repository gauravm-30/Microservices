package com.gaurav.usermicroservice.dto.responses;

import lombok.Getter;

@Getter
public class LoginResp {
    private final String jwtToken;

    public LoginResp(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
