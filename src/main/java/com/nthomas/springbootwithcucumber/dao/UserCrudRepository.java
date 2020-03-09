package com.nthomas.springbootwithcucumber.dao;

import com.nthomas.springbootwithcucumber.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCrudRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserByEmail(String email);
}
