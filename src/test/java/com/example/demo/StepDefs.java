package com.example.demo;


import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.an.E;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

public class StepDefs extends DemoApplicationTests {

    @Autowired
    public UserRepository repository;

    @Autowired
    public List<User> allUsers;

    private RestTemplate restTemplate = new RestTemplate();
    private List<User> allUsersResponse;
    User addedUser;

//
//    @DataTableType
//    public User userEntry(Map<String, String> entry) {
//        String name = entry.get("name");
//        Long id = Long.parseLong(entry.get("id"));
//        return new User(id, name);
//    }

    @Given("Service has only one user with name {} and id {}")
    public void init(String name, Long id) {
        allUsers.clear();
        allUsers.add(new User(id, name));
    }

    @Given("Service has following users")
    public void init(DataTable tableUsers) {
        allUsers.clear();
        List<User> expectedUsers = converTableToUsers(tableUsers);
        allUsers.addAll(expectedUsers);
    }

    @When("Client make a call to API and add user with name {} and empty id")
    public void addingNewUserWithEmptyId(String name) throws JsonProcessingException {
        addingNewUser(name, null);
    }

    @When("Client make a call to API and add user with name {} and id {}")
    public void addingNewUser(String name, Long id) throws JsonProcessingException {
        User user = new User(id, name);
        ObjectMapper objectMapper = new ObjectMapper();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(objectMapper.writeValueAsString(user), headers);

        String url = "http://localhost:8080/users";
        try{
            restTemplate.postForEntity(url, requestEntity, String.class);
        }catch (Exception e){
            errorResponse = e;
        }
    }

    Exception errorResponse;

    @Then("Api respond with conflict response and message contains {}")
    public void conflictRequest(String message) {
        assertThat(errorResponse.getClass(), is(HttpClientErrorException.Conflict.class));
        assertThat(((HttpClientErrorException.Conflict)errorResponse).getStatusCode(), is(HttpStatus.CONFLICT));
        assertThat(errorResponse.getMessage(), containsString(message));
    }

    @Then("Api respond with bad request and message contains {}")
    public void badRequest(String message) {
        assertThat(errorResponse.getClass(), is(HttpClientErrorException.class));
        assertThat(((HttpClientErrorException)errorResponse).getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertThat(errorResponse.getMessage(), containsString(message));
    }

    @When("Client make a call to API to get all users")
    public void getAllUsers() {
        ResponseEntity<List<User>> rateResponse = restTemplate.exchange("http://localhost:8080/users", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });
        allUsersResponse = rateResponse.getBody();
    }

    @Then("API returns only one single user with name {} and id {}")
    public void then(String name, Long id) {
        assertThat(allUsersResponse.size(), is(1));
        assertThat(allUsersResponse.get(0), is(new User(id, name)));
    }


    @Then("API returns following users")
    public void then(DataTable tableUsers) {
        List<User> expectedUsers = converTableToUsers(tableUsers);
        allUsers.addAll(expectedUsers);
        assertThat(allUsersResponse, is(expectedUsers));
    }

    private List<User> converTableToUsers(DataTable dataTable) {
        return dataTable.cells().stream().skip(1)
                .map(record -> new User(Long.valueOf(record.get(1)), record.get(0)))
                .collect(Collectors.toList());
    }

}