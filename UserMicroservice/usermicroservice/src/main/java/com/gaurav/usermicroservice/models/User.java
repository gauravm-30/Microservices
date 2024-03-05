package com.gaurav.usermicroservice.models;


import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name="customers")
//We cannot use  'user' keyword in table name as it is reserved keyword in Postgresql
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name ;
    private String username;
    private String password;
    private String email;

//    We are using set as it should have unique values only .
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id")
            )
    Set<Role> roles;
}
