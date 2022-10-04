package ru.batullin.springboot_bootstrap.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.batullin.springboot_bootstrap.model.User;
import ru.batullin.springboot_bootstrap.repository.UserRepository;

import java.security.InvalidParameterException;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void addUser(User user) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    @Override
    @Transactional
    public void updateUserById(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null &&
                !userRepository.findByUsername(user.getUsername()).getId().equals(user.getId())) {
            throw new InvalidParameterException("Cannot save user, such email already exists in the database: "
                    + user.getUsername());
        }
        if (user.getPassword().isEmpty()) {
            user.setPassword(userRepository.findById(user.getId()).get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }
}
