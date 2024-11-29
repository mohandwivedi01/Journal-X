package net.backend.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.backend.journalApp.model.JournalModel;
import net.backend.journalApp.model.UserModel;
import net.backend.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class JournalServices {
    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserServices userServices;

    // Save a journal entry and associate it with the user
    @Transactional
    public void saveJournalEntries(JournalModel journalEntry, String userName) {
        try {
            UserModel user = userServices.findByUserName(userName);
            JournalModel savedJournal = journalRepository.save(journalEntry);
            user.getJournalEntries().add(savedJournal);
            userServices.saveUser(user);
        } catch (Exception e) {
            log.error("An error occurred while saving the entry.", e);
            throw new RuntimeException("An error occurred while saving the entry.", e);
        }
    }

    // Save a journal entry (without user association)
    public void saveJournalEntries(JournalModel journalEntry) {
        journalRepository.save(journalEntry);
    }

    // Find all journal entries
    public List<JournalModel> findAllJournals() {
        return journalRepository.findAll();
    }

    // Find a journal entry by ID
    public Optional<JournalModel> findJournalById(ObjectId id) {
        return journalRepository.findById(id);
    }

    // Delete a journal entry by ID and dissociate it from the user
    @Transactional
    public boolean deleteJournalById(ObjectId id, String userName) {
        boolean removed = false;
        try {
            UserModel user = userServices.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userServices.saveUser(user);
                journalRepository.deleteById(id);
            }
        } catch (Exception e) {
            log.error("An error occurred while deleting the entry.", e);
            throw new RuntimeException("An error occurred while deleting the entry.", e);
        }
        return removed;
    }
}
