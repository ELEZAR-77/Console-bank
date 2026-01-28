package org.example.repository;

import org.example.entities.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository {
    private final List<User> userList = new ArrayList<>();

    public void save(User user) {
        userList.add(user);
    }

    public List<User> findAll() {
        return userList;
    }

    public User findById(Long id) {
        return userList.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Element not found"));
    }
}
