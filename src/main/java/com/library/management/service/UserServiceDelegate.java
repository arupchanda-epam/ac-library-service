package com.library.management.service;

import com.library.management.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class UserServiceDelegate {
    @Autowired
    RestTemplate restTemplate;

//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }


    @HystrixCommand(fallbackMethod = "callUserServiceGetUsers_Fallback")
    public Iterable<User> getUsers() {
        String url = "http://localhost:8080/users";
        Iterable<User> response = restTemplate.getForObject(url, Iterable.class);
        return response;
    }

    @HystrixCommand(fallbackMethod = "callUserServiceGetUser_Fallback", ignoreExceptions = {HttpClientErrorException.NotFound.class})
    public Optional<User> getUser(Long id) {
        String url = "http://localhost:8080/users/{id}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));
        Optional<User> response = restTemplate.getForObject(url, Optional.class, params);
        return response;

    }

    private Optional<User> callUserServiceGetUser_Fallback(Long id) {
        Optional<User> response = null;
        User user = new User(id, "Dummy Individual User " + id);
        response = Optional.ofNullable(user);
        return response;
    }

    @SuppressWarnings("")
    private Iterable<User> callUserServiceGetUsers_Fallback() {
        Iterable<User> response = null;
        List<User> userList = new ArrayList<>();
        userList.add(new User(101L, "Dummy User 1"));
        response = userList;
        return response;
    }


    @HystrixCommand(fallbackMethod = "callUserServiceSaveUser_Fallback")
    public String addUser(User user) {

        final String uri = "http://localhost:8080/users";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result = restTemplate.postForEntity(uri, user, String.class);

        return "User with Id = " +user.getId()+ "  return Response Code = " + result.getStatusCodeValue();
    }

    @HystrixCommand(fallbackMethod = "callUserServiceDeleteUser_Fallback", ignoreExceptions = {HttpServerErrorException.InternalServerError.class})
    public String deleteUser(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        final String uri = "http://localhost:8080/users/{id}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));

        restTemplate.delete(uri, params);
        String message = "";
        message = "Record deleted...for User with : " + id;
        return message;
    }

    private String callUserServiceSaveUser_Fallback(User user) {
        String message = "";
        message = "Record could not be saved as User service is down...";
        return message;
    }

    @SuppressWarnings("")
    private String callUserServiceDeleteUser_Fallback(Long id) {
        String message = "";
        message = "Record could not be deleted as User service is down...";
        return message;
    }

}

