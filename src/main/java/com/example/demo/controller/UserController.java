package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.model.ValidationError;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserValidationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UserController {

    @Autowired
    private UserValidationService userValidationService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    @PostMapping("/users")
    public ResponseEntity createNewUser(@RequestBody User newUser) {
        try {
            List<ValidationError> validationErrors = userValidationService.validateUser(newUser);
            if (!validationErrors.isEmpty()) {
                return ResponseEntity.badRequest().body(validationErrors);
            }
            User addedUser = userRepository.addUser(newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
        } catch (IllegalArgumentException illegalArgumentException) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(illegalArgumentException.getMessage());
        } catch (Exception e) {
            String uuid = UUID.randomUUID().toString();
            log.error("error occurred while creating new user: " + e.getMessage() + ". Incident id: " + uuid);
            return ResponseEntity.internalServerError().body("Something went wrong, incident ID: " + uuid);
        }
    }

}
