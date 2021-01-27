package com.library.management.model;

public class Book {


    private Long bookid;
    private String bookname;
    private String author;
    private String category;
    private String description;

//    public Book() {
//    }

    public Book(Long bookid, String bookname, String author, String category, String description) {
        this.bookid = bookid;
        this.bookname = bookname;
        this.author = author;
        this.category = category;
        this.description = description;
    }

    public Long getBookid() {
        return bookid;
    }

    public String getBookname() {
        return bookname;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }
}
