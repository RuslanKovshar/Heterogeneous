package com.kovshar.heterogeneous.repository;

import com.kovshar.heterogeneous.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
}
