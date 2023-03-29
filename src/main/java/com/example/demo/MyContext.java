package com.example.demo;

import com.example.demo.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Configuration
public class MyContext {

    @Bean
    List<User> initUsers(){
        List<User> users = new ArrayList<>();
        users.add(User.builder().id(1L).name("Peter").build());
        users.add(User.builder().id(2L).name("John").build());
        return users;
    }

}
