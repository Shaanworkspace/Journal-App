package com.journalapp.service;

import com.journalapp.Repository.JournalEntryRepository;
import com.journalapp.Repository.UserRepository;
import com.journalapp.entity.JournalEntry;
import com.journalapp.entity.UsersEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Controller
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;


    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }

    @Transactional
    public void saveEntryWithUser(JournalEntry journalEntry,String username){
       try {
           UsersEntry user = userService.findByUsername(username);
           JournalEntry saved = journalEntryRepository.save(journalEntry);
           user.getJournalEntry().add(saved);
           userService.saveEntry(user);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
    }
    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId myid){
        return journalEntryRepository.findById(myid);
    }

    public void deleteById(ObjectId myid){
         journalEntryRepository.deleteById(myid);
    }
    public void deleteEntryFromUserToo(ObjectId myid,String username){
        UsersEntry byUsername = userService.findByUsername(username);
        byUsername.getJournalEntry().removeIf(entry->entry.getId()==myid);
        userService.saveEntry(byUsername);
        journalEntryRepository.deleteById(myid);
    }

    public void updateById(){

    }


}
