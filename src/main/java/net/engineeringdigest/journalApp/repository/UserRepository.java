package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

//public interface UserRepository extends MongoRepository<UserModel, Object> {
//    UserModel findByUserName(String user);
//}


public interface UserRepository extends  MongoRepository<UserModel, Object>{
    UserModel findByUserName(String user);
}