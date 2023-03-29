package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ValidationError {
    String error;
    String currentValue;
}
