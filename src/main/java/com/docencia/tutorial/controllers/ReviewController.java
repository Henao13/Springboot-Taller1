package com.docencia.tutorial.controllers;

import com.docencia.tutorial.models.Book;
import com.docencia.tutorial.models.Review;
import com.docencia.tutorial.repositories.BookRepository;
import com.docencia.tutorial.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/books/{bookId}/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private BookRepository bookRepository;

    // Crear una nueva reseña con nombre
    @PostMapping
    public String addReview(@PathVariable Long bookId, @RequestParam String name, @RequestParam String description) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            Review review = new Review(name, description, book);
            review.setLikes(new Random().nextInt(100)); // Generar likes aleatorios
            reviewRepository.save(review);
        }
        return "redirect:/books/" + bookId;
    }

    // Mostrar formulario para editar reseña
    @GetMapping("/{reviewId}/edit")
    public String editReviewForm(@PathVariable Long bookId, @PathVariable Long reviewId, Model model) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        model.addAttribute("review", review);
        model.addAttribute("bookId", bookId);
        return "review/edit";
    }

    // Actualizar una reseña
    @PostMapping("/{reviewId}/update")
    public String updateReview(@PathVariable Long bookId, @PathVariable Long reviewId,
                                @RequestParam("name") String name,
                                @RequestParam("description") String description) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        review.setName(name);
        review.setDescription(description);
        reviewRepository.save(review);
        return "redirect:/books/" + bookId;
    }

    // Eliminar una reseña
    @PostMapping("/{reviewId}/delete")
    public String deleteReview(@PathVariable Long bookId, @PathVariable Long reviewId) {
        reviewRepository.deleteById(reviewId);
        return "redirect:/books/" + bookId;
    }
}