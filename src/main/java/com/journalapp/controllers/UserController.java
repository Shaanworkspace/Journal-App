package com.journalapp.controllers;

import com.journalapp.entity.UsersEntry;
import com.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UsersEntry> createUsers(@RequestBody UsersEntry newEntry){
        try{
            userService.saveEntry(newEntry);
            return new ResponseEntity<>(newEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<UsersEntry>> getAllUsers(){
        List<UsersEntry> listOfUsersEntry = userService.findAllUser();
        return new ResponseEntity<>(listOfUsersEntry, HttpStatus.OK);
    }

    @GetMapping("/id/{userId}")
    public ResponseEntity<Optional<UsersEntry>> findByUserId(@PathVariable ObjectId userId){
        Optional<UsersEntry> userById = userService.findUserById(userId);
        if(userById.isPresent()){
            return new ResponseEntity<>(userById,HttpStatus.FOUND);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId userId){
        userService.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity<UsersEntry> updateUserById(@PathVariable String username,@RequestBody UsersEntry updatedEntry){
        UsersEntry oldUser = userService.findByUsername(username);

        if(oldUser != null){
            oldUser.setUsername(updatedEntry.getUsername());
            oldUser.setPassword(updatedEntry.getPassword());
            userService.saveEntry(oldUser);
            return new ResponseEntity<>(oldUser,HttpStatus.CREATED);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
