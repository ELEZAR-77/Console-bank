package org.example.services;

import org.example.entities.User;
import org.example.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
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

    public User createUser(String login) throws InstanceAlreadyExistsException {

        idCounter++;
        User user = new User(idCounter, login, List.of(accountService.createStartAccount(idCounter)));

        boolean isLoginExist = userRepository.findAll().stream().anyMatch(u -> u.equals(user));
        if (isLoginExist) {
            throw new InstanceAlreadyExistsException("This login already exist!");
        }

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
