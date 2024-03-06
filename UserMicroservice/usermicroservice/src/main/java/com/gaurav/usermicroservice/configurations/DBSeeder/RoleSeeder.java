package com.gaurav.usermicroservice.configurations.DBSeeder;

import com.gaurav.usermicroservice.models.Role;
import com.gaurav.usermicroservice.repositories.RoleRepository;
import com.gaurav.usermicroservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RoleSeeder implements CommandLineRunner {


    //Use service class instead of directly interacting with the repository
    //class
    @Autowired
    RoleRepository roleRepository;
    @Override
    public void run(String... args) throws Exception {
        //Check whether the table is empty or not ,based on that store the record in the table
        if(roleRepository.count()==0) {
            List<Role> roles = new ArrayList<>();
            roles.add(new Role("ROLE_ADMIN"));
            roles.add(new Role("ROLE_USER"));
            roleRepository.saveAll(roles);
        }
    }
}
