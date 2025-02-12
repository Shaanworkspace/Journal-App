package com.journalapp.service;

import com.journalapp.Repository.UserRepository;
import com.journalapp.entity.JournalEntry;
import com.journalapp.entity.UserPrincipal;
import com.journalapp.entity.UsersEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public void saveEntry(UsersEntry usersEntry){
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
}
