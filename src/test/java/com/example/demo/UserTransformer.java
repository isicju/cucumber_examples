package com.example.demo;

import com.example.demo.model.User;
import io.cucumber.datatable.TableEntryTransformer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserTransformer implements TableEntryTransformer<User> {

    @Override
    public User transform(Map<String, String> entry) {
        String name = entry.get("name");
        Long id = Long.parseLong(entry.get("id"));
        return new User(id,name);
    }

    public List<User> transform(List<Map<String, String>> table) {
        List<User> users = new ArrayList<>();
        for (Map<String, String> entry : table) {
            users.add(transform(entry));
        }
        return users;
    }
}
