package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.model.JournalModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepository extends MongoRepository<JournalModel, ObjectId> {
}


