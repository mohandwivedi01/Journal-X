package net.engineeringdigest.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.engineeringdigest.journalApp.model.UserModel;
import net.engineeringdigest.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserServices {

    @Autowired
    private UserRepository userRepository;

//    Logger logger = LoggerFactory.getLogger(UserServices.class);

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveUser(UserModel user){
//        log.info("88888888888888888888888888888888888888888888");
        userRepository.save(user);
    }

    public boolean saveNewUser(UserModel user){
        try{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(Arrays.asList("USER"));
            userRepository.save(user);
            return true;

        }catch (Exception e){
//            logger.info("fdgxcvjkjlkjvgfd");
            return false;
        }
    }

    public List<UserModel> getAll(){
        return userRepository.findAll();
    }

    public Optional<UserModel> getUserById(ObjectId id){
        return userRepository.findById(id);
    }

    public Optional<UserModel> deleteUserById(ObjectId id){
        Optional<UserModel> user = userRepository.findById(id);
        userRepository.deleteById(id);
        return user;
    }

    public UserModel findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

}
