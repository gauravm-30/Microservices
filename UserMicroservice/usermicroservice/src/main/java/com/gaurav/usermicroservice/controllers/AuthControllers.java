package com.gaurav.usermicroservice.controllers;


import com.gaurav.usermicroservice.dto.requests.LoginReq;
import com.gaurav.usermicroservice.dto.requests.SignUpReq;
import com.gaurav.usermicroservice.models.Role;
import com.gaurav.usermicroservice.models.User;
import com.gaurav.usermicroservice.repositories.RoleRepository;
import com.gaurav.usermicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthControllers {

    /*
    * Since there is no constructor and no setter defined ,be default no-arg constructor is called. Also,
    *   dependencies are getting injected  still because of field injection using Autowired.
    */
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("Welcome to spring security project", HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginReq loginReq){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginReq.getUsernameOrEmail(), loginReq.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@RequestBody SignUpReq signUpReq) {

    // add check for username exists in a DB
    if (userRepository.existsByUsername(signUpReq.getUsername())) {
      return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
    }

    // add check for email exists in DB
    if (userRepository.existsByEmail(signUpReq.getEmail())) {
      return new ResponseEntity<>("Email is already taken!", HttpStatus.BAD_REQUEST);
    }
    // create user object
    User user = new User();
    user.setName(signUpReq.getName());
    user.setUsername(signUpReq.getUsername());
    user.setEmail(signUpReq.getEmail());
    user.setPassword(passwordEncoder.encode(signUpReq.getPassword()));

    Role roles = roleRepository.findByName("ROLE_ADMIN").get();
    user.setRoles(Collections.singleton(roles));

    userRepository.save(user);

    return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
}
}
