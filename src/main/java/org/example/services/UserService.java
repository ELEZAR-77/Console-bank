package org.example.services;

import org.example.entities.User;
import org.example.transactional.TransactionalHandler;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@Service
public class UserService {
    Long idCounter = 0L;
    private final AccountService accountService;
    private final TransactionalHandler transactionalHandler;
    private final SessionFactory sessionFactory;

    public UserService(AccountService accountService, TransactionalHandler transactionalHandler, SessionFactory sessionFactory) {
        this.accountService = accountService;
        this.transactionalHandler = transactionalHandler;
        this.sessionFactory = sessionFactory;
    }

    public User createUser(String login) throws InstanceAlreadyExistsException {

        User user = new User(login);

        boolean isLoginExist = showAllUsers().stream().anyMatch(u -> u.equals(user));
        if (isLoginExist) {
            throw new InstanceAlreadyExistsException("This login already exist!");
        }

        return transactionalHandler.executeTransactional(session -> {
            session.persist(user);
            session.persist(accountService.createStartAccount(user));
            return user;
        });
    }

    public List<User> showAllUsers() {
        return transactionalHandler.executeTransactional(session -> {
            return session.createQuery(
                    """
                    SELECT u FROM User u
                    left join fetch u.accountList a
                      """, User.class)
                    .list();
        });
    }

    public User getUserById(Long id) {
        try(Session session = sessionFactory.openSession()) {
            return session.find(User.class, id);
        }
    }
}
