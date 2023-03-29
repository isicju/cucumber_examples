package com.example.demo.repository;

import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class UserRepository {
    private List<User> allUsers;

    public User addUser(User user) {
        if (allUsers.stream().anyMatch(user1 -> user1.getId().equals(user.getId()))) {
            throw new IllegalArgumentException("User with id: " + user.getId() + " already exists");
        }
        allUsers.add(user);
        return user;
    }

    public Optional<User> getUserById(long id) {
        return allUsers.stream().filter(user -> user.getId() == id).findFirst();
    }

    public List<User> getAllUsers() {
        return allUsers;
    }

}
