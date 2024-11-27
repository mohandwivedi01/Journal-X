package net.engineeringdigest.journalApp.repository;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.engineeringdigest.journalApp.model.ConfigJournalAppModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppModel, ObjectId> {

}