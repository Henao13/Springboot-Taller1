package com.docencia.tutorial.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String author; // Nuevo campo
    private Integer price;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    // Constructor vacío para JPA
    public Book() {}

    public Book(String title, String author, Integer price) {
        this.title = title;
        this.author = author;
        this.price = price;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }

    public List<Comment> getComments() { return comments; }
    public void setComments(List<Comment> comments) { this.comments = comments; }
    public void addComment(Comment comment) { this.comments.add(comment); }
}
