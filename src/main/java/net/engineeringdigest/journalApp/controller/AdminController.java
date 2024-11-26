package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.model.UserModel;
import net.engineeringdigest.journalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers(){
        System.out.println("chal raha hai ********************");
        List<UserModel> allUsers =  userServices.getAll();
        if (allUsers != null && !allUsers.isEmpty()){
            System.out.println("******************: "+allUsers);
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
