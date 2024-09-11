package com.oblig1.oblig1.Service;

import com.oblig1.oblig1.Model.User;
import com.oblig1.oblig1.Repo.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    // Check if a username is already taken
    public boolean isUsernameTaken(String username) {
        return userRepo.findByUsername(username).isPresent();
    }

    // Register a new user
    public void registerUser(User user) {
        userRepo.save(user);
    }

    // Login a user
    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            User existingUser = user.get();
            if (existingUser.getPassword().equals(password)) {  // Assuming passwords are stored as plain text (not recommended)
                return Optional.of(existingUser);
            }
        }
        return Optional.empty();
    }

    // Find a user by their username
    public Optional<User> findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    // Update user details
    public Optional<User> updateUser(String username, User updatedUser) {
        Optional<User> existingUser = userRepo.findByUsername(username);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setPassword(updatedUser.getPassword()); // Be sure to hash the password if needed
            return Optional.of(userRepo.save(user));  // Save and return updated user
        }
        return Optional.empty();  // Return empty if user not found
    }

    // Delete a user by username
    public void deleteUser(String username) {
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            userRepo.delete(user.get());
        }
    }
}
