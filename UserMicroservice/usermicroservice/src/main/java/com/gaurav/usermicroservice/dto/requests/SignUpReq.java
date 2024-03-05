package com.gaurav.usermicroservice.dto.requests;

import lombok.Data;

@Data
public class SignUpReq {
    private String name;
    private String username;
    private String email;
    private String password;
}
