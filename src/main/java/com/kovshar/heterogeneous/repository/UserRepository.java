package com.kovshar.heterogeneous.repository;

import com.kovshar.heterogeneous.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Long> {
    List<User> findAllByIdIn(Long[] ids);
}
