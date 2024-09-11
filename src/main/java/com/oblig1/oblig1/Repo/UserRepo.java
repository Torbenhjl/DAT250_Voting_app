package com.oblig1.oblig1.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oblig1.oblig1.Model.User;

public interface UserRepo extends JpaRepository<User, Long>{

    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndPassword(String username, String password);

    }

