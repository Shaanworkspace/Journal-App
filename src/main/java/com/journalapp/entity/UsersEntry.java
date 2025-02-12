package com.journalapp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "user")
@Data
public class UsersEntry {

    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String username;
    @NonNull
    private String password;

    @DBRef
    private List<JournalEntry> journalEntry = new ArrayList<>();

    /*
     @DBRef
    private JournalEntry journalEntry;

    if we do like this

    [
    {
        "username": "shaan",
        "password": "123",
        "id": {
            "timestamp": 1739257425,
            "date": "2025-02-11T07:03:45.000+00:00"
        },
        "journalEntry": null
    }
]
     */


}
