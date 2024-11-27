package net.engineeringdigest.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.model.JournalModel;
import net.engineeringdigest.journalApp.model.UserModel;
import net.engineeringdigest.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class JournalServices {
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private UserServices userServices;

//    LoggerFactory is an utility class, each logger associated to a class
//    private Logger logger = LoggerFactory.getLogger(JournalServices.class);

    @Transactional
    public void saveJournalEntries(JournalModel journalEntry, String userName){
        try {
            UserModel user = userServices.findByUserName(userName);
            JournalModel savedJournal = journalRepository.save(journalEntry);
            user.getJournalEntries().add(savedJournal);
            userServices.saveUser(user);
        }catch (Exception e){
//            logger.info("hahahahahhahahahhahahahahahha");
            log.info("An error occurred while saving the entry.",e);
            throw new RuntimeException("An error occurred while saving the entry.",e);
        }
    }

    public void saveJournalEntries(JournalModel journalEntry){
        journalRepository.save(journalEntry);
    }

    public List<JournalModel> findAllJournals(){
        return journalRepository.findAll();
    }

    public Optional<JournalModel> findJournalById(ObjectId id){
        return journalRepository.findById(id);
    }

    @Transactional
    public boolean deleteJournalById(ObjectId id, String userName){
        boolean removed = false;
        try{
            UserModel user = userServices.findByUserName(userName);
            removed = user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if (removed){
                userServices.saveUser(user);
                journalRepository.deleteById(id);
            }
        }catch (Exception e){
            System.out.println(e);
            throw new RuntimeException("An error occurec while deleting the entry. ",e);
        }
        return removed; 
    }

}
