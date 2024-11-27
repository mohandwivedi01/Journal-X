package net.engineeringdigest.journalApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuotesServices {

    @Autowired
    private UserServices userServices;

    private  static final String APIKEY = "1PAzUW1XfuqMBbvQncgRD7TJgrpCCXdYkL";
}
