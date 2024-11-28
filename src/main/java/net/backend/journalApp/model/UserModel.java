package net.backend.journalApp.model;

import lombok.*;
import org.bson.types.ObjectId;
//import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

//import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
@NoArgsConstructor
public class UserModel {
    @Id
    private ObjectId id;

    @NonNull
    @Indexed(unique = true)
    private String userName;
    @NonNull
    private String password;

    @DBRef
    private List<JournalModel> journalEntries = new ArrayList<>();
    private List<String> roles;

    @CreatedDate//from Spring Data Auditing Automatically sets the value of createdAt to the timestamp when the document is first saved to MongoDB.
    private LocalDateTime createdAt;
    @LastModifiedDate//from Spring Data Auditing Automatically updates the value of updatedAt to the current timestamp whenever the document is modified.
    private LocalDateTime updatedAt;

}
