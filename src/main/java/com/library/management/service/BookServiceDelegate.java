package com.library.management.service;

import com.library.management.model.Book;
import com.library.management.model.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.http.HttpResponse;
import java.util.*;

@Service
public class BookServiceDelegate {

    @Autowired
    RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @HystrixCommand(fallbackMethod = "callBookServiceGetBooks_Fallback")
    public Iterable<Book> getBooks() {
        String url = "http://localhost:8083/books";
        Iterable<Book> response = restTemplate.getForObject(url, Iterable.class);
        return response;
    }


    private Iterable<Book> callBookServiceGetBooks_Fallback(){
        Iterable<Book> response = null;
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(101L,"Dummy Book1","Dummy Author1","Category1", "Description1"));
        response = bookList;
        return response;
    }


    @HystrixCommand(fallbackMethod = "callBookServiceGetBook_Fallback")
    public Optional<Book> getBook(Long id) {
        String url = "http://localhost:8083/books/{id}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));
        Optional<Book> response = restTemplate.getForObject(url, Optional.class, params);
        return response;
    }


    private Iterable<Book> callBookServiceGetBook_Fallback(){
        Iterable<Book> response = null;
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(101L,"Dummy Individual Book1","Dummy Individual Author1","Individual Category1", "Individual Description1"));
        response = bookList;
        return response;
    }


    @HystrixCommand(fallbackMethod = "callBookServiceSaveBook_Fallback")
    public String addBook(Book book) {

        final String uri = "http://localhost:8083/books";
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> result = restTemplate.postForEntity(uri, book, String.class);

        return "Book with Id = " +book.getBookid()+ "  return Response Code = " + result.getStatusCodeValue();
    }

    @HystrixCommand(fallbackMethod = "callBookServiceDeleteBook_Fallback", ignoreExceptions = {HttpServerErrorException.InternalServerError.class})
    public String deleteBook(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        final String uri = "http://localhost:8083/books/{id}";
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", String.valueOf(id));

        restTemplate.delete(uri, params);
        String message = "";
        message = "Record deleted...for Book with : " + id;
        return message;
    }

    private String callBookServiceSaveBook_Fallback(Book book) {
        String message = "";
        message = "Record could not be saved as Book service is down...";
        return message;
    }

    @SuppressWarnings("")
    private String callBookServiceDeleteBook_Fallback(Long id) {
        String message = "";
        message = "Record could not be deleted as Book service is down...";
        return message;
    }

}
