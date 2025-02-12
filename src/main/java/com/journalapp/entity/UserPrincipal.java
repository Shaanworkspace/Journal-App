package com.journalapp.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrincipal implements UserDetails {

    //No need to import if in same package
    private UsersEntry usersEntry;

    public UserPrincipal(UsersEntry usersEntry) {
        this.usersEntry=usersEntry;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }



    @Override
    public String getPassword() {
        return usersEntry.getPassword();
    }

    @Override
    public String getUsername() {
        return usersEntry.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //yes not expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        //assume account is enable so make it true
        return true;
    }
}
