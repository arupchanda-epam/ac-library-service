package com.library.management.controller;

import com.library.management.model.Book;
import com.library.management.model.User;
import com.library.management.service.BookServiceDelegate;
import com.library.management.service.LibraryService;
import com.library.management.service.UserServiceDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Optional;

@RestController
public class LibraryController {
    @Autowired
    LibraryService libraryService;

    @Autowired
    BookServiceDelegate bookServiceDelegate;

    @Autowired
    UserServiceDelegate userServiceDelegate;

    @GetMapping("/library/books")
    private Iterable<Book> getBooks() {
        return bookServiceDelegate.getBooks();
    }

    @GetMapping("/library/books/{id}")
    private Optional<Book> getBooks(@PathVariable("id") Long id) {
        return bookServiceDelegate.getBook(id);
    }

    @PostMapping("/library/books")
    private String addBook(@RequestBody Book book) {
        return bookServiceDelegate.addBook(book);
    }

    @DeleteMapping("/library/books/{id}")
    public String deleteBook(@PathVariable Long id) {
        return bookServiceDelegate.deleteBook(id);
    }


    @GetMapping("/library/users")
    private Iterable<User> getUsers() {
        return userServiceDelegate.getUsers();
    }

    @GetMapping("/library/users/{id}")
    private Optional<User> getUser(@PathVariable("id") Long id) {
        return userServiceDelegate.getUser(id);
    }

    @PostMapping("/library/users")
    private String addUser(@RequestBody User user) {
        return userServiceDelegate.addUser(user);
    }

    @DeleteMapping("/library/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userServiceDelegate.deleteUser(id);
    }
}
