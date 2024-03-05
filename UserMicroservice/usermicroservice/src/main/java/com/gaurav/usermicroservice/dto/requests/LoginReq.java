package com.gaurav.usermicroservice.dto.requests;

import lombok.Data;
import lombok.Setter;

@Data
public class LoginReq {
    private String usernameOrEmail;
    private String password;
}
