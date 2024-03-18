package com.gaurav.usermicroservice.services;


import com.gaurav.usermicroservice.models.Role;
import com.gaurav.usermicroservice.models.User;
import com.gaurav.usermicroservice.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService  implements UserDetailsService{

    private UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email: "+ usernameOrEmail));

        //here when we call the getRoles only then Hibernate will fire the query on the database
        //since each interaction with database require the transaction it means while calling getRoles(),
        //it should also be in the transaction.If not then it will give LazyInitializationException
        Set<Role>  userRoles=user.getRoles();

        Set<GrantedAuthority> authorities = userRoles
                .stream()
                .map((role) -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);    }
}
