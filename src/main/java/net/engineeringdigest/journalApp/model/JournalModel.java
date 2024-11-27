package net.engineeringdigest.journalApp.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection="journals")
@Data   //from Lombok Generates boilerplate code like getters, setters, toString, equals, and hashCode methods for the class.
@NoArgsConstructor  //from Lombok Generates a no-argument constructor for the class.
public class JournalModel {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;

    @CreatedDate //from Spring Data Auditing Automatically sets the value of createdAt to the timestamp when the document is first saved to MongoDB.
    private LocalDateTime createdAt;
    @LastModifiedDate  //from Spring Data Auditing Automatically updates the value of updatedAt to the current timestamp whenever the document is modified.
    private LocalDateTime updatedAt;
}


/*
@Document(collection="Journals")
@Data
public class JournalsModel{



}
 */