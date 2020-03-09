package com.nthomas.springbootwithcucumber.service;

import com.nthomas.springbootwithcucumber.dao.UserCrudRepository;
import com.nthomas.springbootwithcucumber.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Transactional
@Service
public class UserService {

    private final UserCrudRepository userCrudRepository;

    @Autowired
    public UserService(final UserCrudRepository userCrudRepository) {
        this.userCrudRepository = userCrudRepository;
    }

    public User getUser(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id is required to find a user.");
        }

        Optional<User> user = userCrudRepository.findById(id);

        if (!user.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Could not find user");
        } else {
            return user.get();
        }
    }

    public User saveUser(User user) {
        if (user.getEmail() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email is required.");
        }

        checkEmail(user.getEmail());

        return userCrudRepository.save(user);
    }

    private void checkEmail(String email) {
        Optional<User> emailUser = userCrudRepository.findUserByEmail(email);
        if (emailUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provided email is already in use.");
        }
    }

    public User updateUser(User user) {
        User foundUser = getUser(user.getId());

        if (!foundUser.getEmail().equalsIgnoreCase(user.getEmail())) {
            checkEmail(user.getEmail());
        }

        foundUser.setEmail(user.getEmail());
        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());

        return userCrudRepository.save(foundUser);
    }
}
