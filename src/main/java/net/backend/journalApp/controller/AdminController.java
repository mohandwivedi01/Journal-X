package net.backend.journalApp.controller;

import net.backend.journalApp.model.UserModel;
import net.backend.journalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The `AdminController` class handles administrative endpoints.
 * It provides functionality for administrators, such as fetching all user details.
 */
@RestController // Indicates that this class is a REST controller.
@RequestMapping("/admin") // Maps all endpoints in this class under the "/admin" base URL.
public class AdminController {

    @Autowired
    private UserServices userServices; // Service layer to handle user-related operations.

    /**
     * Retrieves all registered users in the system.
     *
     * @return A `ResponseEntity` containing the list of all users and an HTTP status.
     * - Returns HTTP 200 (OK) with the user list if users are found.
     * - Returns HTTP 404 (NOT_FOUND) if no users are found.
     */
    @GetMapping("/all-users") // Maps this method to the "/admin/all-users" GET endpoint.
    public ResponseEntity<?> getAllUsers() {
        // Fetch all users from the database using the service layer.
        List<UserModel> allUsers = userServices.getAll();

        // Check if the list is not null and contains data.
        if (allUsers != null && !allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK); // Return user list with HTTP 200.
        }

        // Return HTTP 404 if no users are found.
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
