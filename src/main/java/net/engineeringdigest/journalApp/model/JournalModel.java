package net.engineeringdigest.journalApp.model;

import lombok.*;
import org.bson.types.ObjectId;
//import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
//import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

//import java.time.LocalDateTime;

@Document(collection="journals")
@Data
@NoArgsConstructor
public class JournalModel {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
//
//    @CreatedDate
//    private LocalDateTime createdAt;
//    @LastModifiedDate
//    private LocalDateTime updatedAt;
}
