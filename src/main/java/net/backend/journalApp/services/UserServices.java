package net.backend.journalApp.services;

import lombok.extern.slf4j.Slf4j;
import net.backend.journalApp.model.UserModel;
import net.backend.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Service annotation to indicate this class is a service component for Spring
@Service
@Slf4j // Slf4j annotation provides a logger instance, no need for LoggerFactory
public class UserServices {

    // Autowired to inject the UserRepository for database access
    @Autowired
    private UserRepository userRepository;

    // Password encoder for hashing the user password
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Method to save a user to the repository
    public void saveUser(UserModel user){
        userRepository.save(user);  // Save user in the database
    }

    // Method to save a new user with encrypted password
    public boolean saveNewUser(UserModel user){
        try{
            // Encode the user's password before saving
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setRoles(user.getRoles());  // Assign roles to the user
            userRepository.save(user);  // Save the user
            return true;
        }catch (Exception e){
            // Log the error if any exception occurs during user signup
            log.error("Something went wrong during signup for user: {}", user.getUserName(), e);
            return false;  // Return false if there was an error
        }
    }

    // Method to get all users from the repository
    public List<UserModel> getAll(){
        return userRepository.findAll();  // Return all users
    }

    // Method to find a user by their ID
    public Optional<UserModel> getUserById(ObjectId id){
        return userRepository.findById(id);  // Return user if found
    }

    // Method to delete a user by their ID
    public Optional<UserModel> deleteUserById(ObjectId id){
        Optional<UserModel> user = userRepository.findById(id);  // Get user by ID
        userRepository.deleteById(id);  // Delete the user from the repository
        return user;  // Return the deleted user
    }

    // Method to find a user by their username
    public UserModel findByUserName(String userName){
        return userRepository.findByUserName(userName);  // Return the user with the given username
    }
}
