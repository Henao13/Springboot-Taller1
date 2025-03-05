package com.docencia.tutorial.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.docencia.tutorial.models.Book;
import com.docencia.tutorial.repositories.BookRepository;
import com.docencia.tutorial.repositories.ReviewRepository;

import jakarta.annotation.PostConstruct;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @PostConstruct
    public void ensureDefaultBookExists() {
        if (bookRepository.count() == 0) {
            Book defaultBook = new Book("El Principito", "Antoine de Saint-Exupéry", 100);
            bookRepository.save(defaultBook);
        } else {
            // Asegurar que el libro existente tenga un autor
            Book existingBook = bookRepository.findAll().get(0);
            if (existingBook.getAuthor() == null) {
                existingBook.setAuthor("Antoine de Saint-Exupéry");
                bookRepository.save(existingBook);
            }
        }
    }


    @GetMapping("/books")
    public String index(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("title", "Books - Online Store");
        model.addAttribute("subtitle", "List of books");
        model.addAttribute("books", books);
        return "book/index"; // Vista: book/index.html
    }

    @GetMapping("/books/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        model.addAttribute("title", book.getTitle() + " - Online Store");
        model.addAttribute("subtitle", book.getTitle() + " - Book information");
        model.addAttribute("book", book);
        model.addAttribute("reviews", book.getReviews());
        return "book/show"; // Vista: book/show.html
    }


    @PostMapping("/books")
    public String save(Book book) {
        if (book.getTitle() == null || book.getTitle().isEmpty() || book.getPrice() == null) {
            throw new RuntimeException("Title and Price are required");
        }
        bookRepository.save(book);
        return "redirect:/books";
    }

}

