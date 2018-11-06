package com.example.polls.payload;

import java.util.Set;

public class UserBasic {

    private Long id;
    private String firstname;
    private String lastname;
    private String username;
    private String email;
    private Set<RoleBasic> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<RoleBasic> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleBasic> roles) {
        this.roles = roles;
    }
}
