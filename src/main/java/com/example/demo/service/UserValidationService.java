package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.model.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserValidationService {


    public List<ValidationError> validateUser(User user) {
        List<ValidationError> errors = new ArrayList<>();
        if (user == null) {
            errors.add(ValidationError.builder().error("User not valid because is empty").build());
        } else {
            if (user.getId() == null) {
                errors.add(ValidationError.builder().error("User is not valid because it's id is empty ").build());
            }
            if (user.getName() == null || user.getName().length() < 3) {
                errors.add(ValidationError.builder()
                        .error("User is not valid because it's name is empty or is less than 3 chars long!").build());
            }
        }
        return errors;
    }


}
