package com.kovshar.heterogeneous.service;

import com.kovshar.heterogeneous.exceptions.UserNotFoundException;
import com.kovshar.heterogeneous.model.User;
import com.kovshar.heterogeneous.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public List<User> getAllByIds(Long[] ids) {
        return userRepository.findAllByIdIn(ids);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
