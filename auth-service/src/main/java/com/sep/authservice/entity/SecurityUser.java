package com.sep.authservice.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SecurityUser implements UserDetails {
    private String username;
    private Long id;
    private String password;
    private Collection<Role> roles;

    public SecurityUser(AppUser appUser) {

        this.username = appUser.getUsername();
        this.password = appUser.getPassword();
        this.id = appUser.getId();
        this.roles = appUser.getRoles();

    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authoritiess = new ArrayList<>();
        for(Role role: this.roles){
            authoritiess.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authoritiess;
    }

    @Override
    public String getPassword() {
        return null;
    }
}