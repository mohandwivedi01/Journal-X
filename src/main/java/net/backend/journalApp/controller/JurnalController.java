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

@Slf4j // Lombok annotation for logging.
@RestController // Marks this class as a REST controller.
@RequestMapping("/journal") // Maps all endpoints in this class under "/journal".
public class JurnalController {

    @Autowired
    private JournalServices journalServices; // Service for handling journal-related operations.

    @Autowired
    private UserServices userServices; // Service for handling user-related operations.

    /**
     * Fetch all journal entries for the authenticated user.
     *
     * @return A list of journal entries with HTTP 200 (OK) if found,
     *         or HTTP 404 (NOT_FOUND) if no entries are available.
     */
    @GetMapping()
    public ResponseEntity<?> getAllUserJournals() {
        try {
            // Get the currently authenticated user's username.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            // Fetch the user and their journal entries.
            UserModel user = userServices.findByUserName(userName);
            List<JournalModel> all = user.getJournalEntries();

            // Return the entries if available.
            if (all != null && !all.isEmpty()) {
                return new ResponseEntity<>(all, HttpStatus.OK);
            }
        } catch (Exception e) {
            // Log the error and rethrow the exception.
            log.error("An error occurred while fetching all user journals: {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Fetch a specific journal entry by its ID.
     *
     * @param journalId The ID of the journal entry to fetch.
     * @return The journal entry with HTTP 200 (OK) if found, or HTTP 404 (NOT_FOUND) if not found.
     */
    @GetMapping("id/{journalId}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId journalId) {
        try {
            // Get the authenticated user's username.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            // Fetch the user and check if the journal entry exists for them.
            UserModel user = userServices.findByUserName(userName);
            boolean journalExists = user.getJournalEntries()
                    .stream()
                    .anyMatch(journal -> journal.getId().equals(journalId));

            if (journalExists) {
                // Fetch the journal entry if it exists.
                Optional<JournalModel> journal = journalServices.findJournalById(journalId);
                if (journal.isPresent()) {
                    return new ResponseEntity<>(journal, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            log.error("An error occurred while fetching the journal by ID: {}", e.getMessage(), e);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Create a new journal entry for the authenticated user.
     *
     * @param journalEntry The journal entry to create.
     * @return HTTP 201 (CREATED) if the journal entry is successfully created,
     *         or HTTP 400 (BAD_REQUEST) if an error occurs.
     */
    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody JournalModel journalEntry) {
        try {
            // Get the authenticated user's username.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            // Save the journal entry.
            journalServices.saveJournalEntries(journalEntry, userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("An error occurred during creating a new journal entry: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete a journal entry by its ID for the authenticated user.
     *
     * @param id The ID of the journal entry to delete.
     * @return HTTP 200 (OK) if the journal entry is successfully deleted,
     *         or HTTP 204 (NO_CONTENT) if the entry does not exist.
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
        try {
            // Get the authenticated user's username.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();

            // Attempt to delete the journal entry.
            boolean removed = journalServices.deleteJournalById(id, userName);
            return removed ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("An error occurred while deleting the journal by ID: {}", e.getMessage(), e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update an existing journal entry for the authenticated user.
     *
     * @param newJournalEntry The new journal entry data.
     * @param id              The ID of the journal entry to update.
     * @return The updated journal entry with HTTP 200 (OK),
     *         or HTTP 400 (BAD_REQUEST) if the entry does not exist.
     */
    @PutMapping("/id/{id}")
    public ResponseEntity<?> updateJournalEntry(
            @RequestBody JournalModel newJournalEntry,
            @PathVariable ObjectId id
    ) {
        try {
            // Get the authenticated user's username.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            // Fetch the user and check if the journal entry exists for them.
            UserModel user = userServices.findByUserName(username);
            boolean journalExists = user.getJournalEntries()
                    .stream()
                    .anyMatch(journal -> journal.getId().equals(id));

            if (journalExists) {
                // Update the journal entry with new data if it exists.
                JournalModel currJournal = journalServices.findJournalById(id).orElse(null);
                if (currJournal != null) {
                    currJournal.setTitle(newJournalEntry.getTitle() != null
                            ? newJournalEntry.getTitle()
                            : currJournal.getTitle());
                    currJournal.setContent(newJournalEntry.getContent() != null
                            ? newJournalEntry.getContent()
                            : currJournal.getContent());
                    journalServices.saveJournalEntries(currJournal);
                    return new ResponseEntity<>(currJournal, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            log.error("An error occurred while updating the journal entry: {}", e.getMessage(), e);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
