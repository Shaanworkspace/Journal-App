package com.journalapp.controllers;

import com.journalapp.Repository.JournalEntryRepository;
import com.journalapp.entity.JournalEntry;
import com.journalapp.entity.UsersEntry;
import com.journalapp.service.JournalEntryService;
import com.journalapp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class JournalEntryController {

    //as controller will call the service
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;



    @GetMapping
    public List<JournalEntry> getAllEntries(){
        return journalEntryService.getAll();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<JournalEntry>> getAllJournalEntryOfUser(@PathVariable String username){
        UsersEntry userBlock = userService.findByUsername(username);
        List<JournalEntry> listOfJournalEntry = userBlock.getJournalEntry();
        if(listOfJournalEntry != null && !listOfJournalEntry.isEmpty()){
            return new ResponseEntity<>(listOfJournalEntry,HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> getAllEntries(@PathVariable ObjectId myid){
        Optional<JournalEntry> journalEntry = journalEntryService.getById(myid);
        return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }




    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry){
        try{
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(journalEntry);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        }catch (Exception e){
            //Means we have not given json properly
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/user/{username}")
    public ResponseEntity<JournalEntry> createEntryByUserId(@PathVariable String username,@RequestBody JournalEntry journalEntry){
        try{
            journalEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntryWithUser(journalEntry,username);
            return new ResponseEntity<>(journalEntry,HttpStatus.CREATED);
        }catch (Exception e){
            //Means we have not given json properly
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }






    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId){
         journalEntryService.deleteById(myId);
         return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/id/{username}/{myId}")
    public ResponseEntity<?> deleteEntryFromUserToo(@PathVariable ObjectId myId ,@PathVariable String username){
        journalEntryService.deleteEntryFromUserToo(myId,username);
        return new ResponseEntity<>(HttpStatus.OK);
    }






    @PutMapping("/id/{myId}")
    public JournalEntry updateById(@PathVariable ObjectId myId, @RequestBody JournalEntry UpdatedEntry){
        JournalEntry oldEntry = journalEntryService.getById(myId).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(UpdatedEntry.getTitle() !=null ? UpdatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(UpdatedEntry.getContent() != null ? UpdatedEntry.getContent() : oldEntry.getContent());
        }
        journalEntryService.saveEntry(oldEntry);
        return oldEntry;
    }
}
