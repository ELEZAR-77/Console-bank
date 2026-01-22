package org.example.services;

import org.example.entities.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    Long idCounter = 0L;
    private final AccountService accountService;
    private final UserRepository userRepository;

    public UserService(AccountService accountService, UserRepository userRepository) {
        this.accountService = accountService;
        this.userRepository = userRepository;
    }

    public User createUser(String login) {
        if (login == null) throw new IllegalArgumentException("Field can`t be empty!");

        idCounter++;
        User user = new User(idCounter, login, List.of(accountService.createStartAccount(idCounter)));

        userRepository.save(user);
        return user;
    }

    public List<User> showAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        if (id == null) throw new IllegalArgumentException("Field can`t be empty!");

        return userRepository.findById(id);
    }
}
