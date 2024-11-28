package net.backend.journalApp.cache;

import net.backend.journalApp.model.ConfigJournalAppModel;
import net.backend.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> APP_CACHE;

    @PostConstruct
    public void init(){
        APP_CACHE = new HashMap<>();
        List<ConfigJournalAppModel> all = configJournalAppRepository.findAll();
        for (ConfigJournalAppModel configJournalAppModel: all){
            APP_CACHE.put(configJournalAppModel.getKey(), configJournalAppModel.getValue());
        }
    }
}


/*
create a new collenction in mongoDB, collection name=> config_journa_app
INSERT DOCUMENT

 */