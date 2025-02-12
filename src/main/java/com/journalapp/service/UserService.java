package com.journalapp.service;

import com.journalapp.Repository.UserRepository;
import com.journalapp.entity.JournalEntry;
import com.journalapp.entity.UserPrincipal;
import com.journalapp.entity.UsersEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public void saveEntry(UsersEntry usersEntry){
        usersEntry.setPassword(encoder.encode(usersEntry.getPassword()));
        userRepository.save(usersEntry);
    }

    public List<UsersEntry> findAllUser(){
        return userRepository.findAll();
    }

    public Optional<UsersEntry> findUserById(ObjectId userId){
        return userRepository.findById(userId);
    }

    public void deleteById(ObjectId userId){
        userRepository.deleteById(userId);
    }

    public UsersEntry findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public String verify(UsersEntry user){
        //In this method we are getting new means -> we are sending a unauthenticated thing and receiving new authenticated things with a token
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername());
        }
        return "false";
    }

}
