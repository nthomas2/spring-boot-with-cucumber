package com.nthomas.springbootwithcucumber;

import com.nthomas.springbootwithcucumber.dao.UserCrudRepository;
import com.nthomas.springbootwithcucumber.entity.User;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public class UserStepDefinitions {
    @Autowired
    private UserCrudRepository userCrudRepository;

    private User testUser;

    @Given("the User {} does not exist")
    public void given_user_does_not_exist(String email) {
        Optional<User> user = userCrudRepository.findUserByEmail(email);
        user.ifPresent(value -> userCrudRepository.delete(value));
    }

    @Given("the User {} already exists")
    public void given_user_with_email_already_exists(String email) {
        Optional<User> user = userCrudRepository.findUserByEmail(email);
        if (!user.isPresent()) {
            User createUser = new User();
            createUser.setFirstName("firstName");
            createUser.setLastName("lastName");
            createUser.setEmail(email);
            testUser = userCrudRepository.save(createUser);
        } else {
            testUser = user.get();
        }
    }

    @Given("the User has first name of {}")
    public void given_user_has_first_name(String firstName) {
        testUser.setFirstName(firstName);
        testUser = userCrudRepository.save(testUser);
    }

    @Given("the User has last name of {}")
    public void given_user_has_last_name(String lastName) {
        testUser.setLastName(lastName);
        testUser = userCrudRepository.save(testUser);
    }

}
