package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.model.UserModel;
import net.engineeringdigest.journalApp.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserServices userServices;

    @GetMapping
    public String healthCheck(){return "i'm running...";}

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(@RequestBody UserModel user){
        if (userServices.saveNewUser(user))
            return new ResponseEntity<>(HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
