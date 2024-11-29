package net.backend.journalApp.services;

import net.backend.journalApp.model.UserModel;
import net.backend.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Service annotation to indicate this class is a service component for Spring
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    // Autowired to inject the UserRepository for database access
    @Autowired
    private UserRepository userRepository;

    // Method to load user details by username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Retrieve the user from the repository by the given username
        UserModel user = userRepository.findByUserName(username);

        // Check if the user exists
        if (user != null) {
            // Return a User object containing the username, password, and roles
            return User.builder()
                    .username(user.getUserName())  // Set the username
                    .password(user.getPassword())  // Set the password (should be encrypted in real cases)
                    .roles(user.getRoles().toArray(new String[0]))  // Set roles, converting List to Array
                    .build();
        }

        // If user is not found, throw UsernameNotFoundException
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
