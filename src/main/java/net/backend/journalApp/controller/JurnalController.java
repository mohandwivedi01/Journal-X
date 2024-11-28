package net.backend.journalApp.controller;

import lombok.extern.slf4j.Slf4j;
import net.backend.journalApp.model.JournalModel;
import net.backend.journalApp.model.UserModel;
import net.backend.journalApp.services.JournalServices;
import net.backend.journalApp.services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static java.rmi.server.LogStream.log;

@Slf4j
@RestController
@RequestMapping("/journal")
public class JurnalController {

    @Autowired
    private JournalServices journalServices;

    @Autowired
   private UserServices userServices;

    @GetMapping()
    public ResponseEntity<?> getAllUserJournals() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            UserModel user = userServices.findByUserName(userName);
            List<JournalModel> all = user.getJournalEntries();
            if (all != null && !all.isEmpty()) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
        }catch (Exception e){
            log.error("an error occurred during fetching all user journals: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("id/{journalId}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId journalId){
        System.out.println("*******************************");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication);
        String userName = authentication.getName();
        System.out.println("username: "+userName);
        UserModel user = userServices.findByUserName(userName);
        System.out.println("user: "+user);
        boolean journalExists = user.getJournalEntries().stream().anyMatch(journal -> journal.getId().equals(journalId));
        System.out.println("journalExists: "+journalExists);
        if (journalExists){
            Optional<JournalModel> journal = journalServices.findJournalById(journalId);
            if (journal.isPresent())
                return new ResponseEntity<>(journal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalModel journalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            journalServices.saveJournalEntries(journalEntry, userName);

        }catch (Exception e){
            log("an error occured during creating new jouirnal entry");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed = journalServices.deleteJournalById(id, userName);
        if (removed)
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntry(
            @RequestBody JournalModel newJournalEntry,
            @PathVariable ObjectId id
    ){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        UserModel user = userServices.findByUserName(username);
        boolean journalExists = user.getJournalEntries().stream().anyMatch(journal -> journal.getId().equals(id));
        if(journalExists){
            JournalModel currJournal = journalServices.findJournalById(id).orElse(null);
            currJournal.setTitle(newJournalEntry.getTitle() != null  ? newJournalEntry.getTitle() : currJournal.getTitle());
            currJournal.setContent(newJournalEntry.getContent() != null ? newJournalEntry.getContent() : currJournal.getContent());
            journalServices.saveJournalEntries(currJournal);
            return new ResponseEntity<>(currJournal, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
