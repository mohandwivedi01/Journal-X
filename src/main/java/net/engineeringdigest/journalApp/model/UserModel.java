package net.engineeringdigest.journalApp.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private List<JournalModel>  journalEntries = new ArrayList<>();
    private List<String> roles;
}
