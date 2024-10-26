package net.engineeringdigest.journalApp.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="journals")
@Data
@NoArgsConstructor

public class JournalModel {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
}
