package com.journalapp.Repository;

import com.journalapp.entity.UsersEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UsersEntry, ObjectId> {
    UsersEntry findByUsername(String username);
}
