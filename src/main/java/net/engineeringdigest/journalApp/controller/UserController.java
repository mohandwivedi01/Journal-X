package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.model.UserModel;
import net.engineeringdigest.journalApp.services.UserServices;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @GetMapping("get-all")
    public List<UserModel> getAllUsers(){
        return userServices.getAll();
    }

    @GetMapping("/id{id}")
    public Optional<UserModel> getUserById(@PathVariable ObjectId id){
        return userServices.getUserById(id);
    }



    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody UserModel user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();;

        UserModel isUser = userServices.findByUserName(userName);
        if(isUser != null){
            isUser.setUserName(user.getUserName());
            isUser.setPassword(user.getPassword());
            userServices.saveNewUser(isUser);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
