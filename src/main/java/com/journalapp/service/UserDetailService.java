package com.journalapp.service;

import com.journalapp.Repository.UserRepository;
import com.journalapp.entity.UserPrincipal;
import com.journalapp.entity.UsersEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsersEntry user = userRepository.findByUsername(username);
        if(user ==null){
            System.out.println("No user found");
            //let the system know by the exception
            throw new UsernameNotFoundException("User Not found");
        }

        //If there is a match in password this will be passed from it
        return new UserPrincipal(user);
    }
}
