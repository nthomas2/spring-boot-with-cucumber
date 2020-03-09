package com.nthomas.springbootwithcucumber.dao;

import com.nthomas.springbootwithcucumber.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCrudRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByEmail(String email);
}
