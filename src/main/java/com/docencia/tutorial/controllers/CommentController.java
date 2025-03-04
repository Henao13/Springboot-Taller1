package com.docencia.tutorial.controllers;

import com.docencia.tutorial.models.Book;
import com.docencia.tutorial.models.Comment;
import com.docencia.tutorial.repositories.BookRepository;
import com.docencia.tutorial.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Random;

@Controller
@RequestMapping("/books/{bookId}/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BookRepository bookRepository;

    // Crear una nueva reseña con nombre
    @PostMapping
    public String addComment(@PathVariable Long bookId, @RequestParam String name, @RequestParam String description) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            Comment comment = new Comment(name, description, book);
            comment.setLikes(new Random().nextInt(100)); // Generar likes aleatorios
            commentRepository.save(comment);
        }
        return "redirect:/books/" + bookId;
    }

    // Mostrar formulario para editar reseña
    @GetMapping("/{commentId}/edit")
    public String editCommentForm(@PathVariable Long bookId, @PathVariable Long commentId, Model model) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        model.addAttribute("comment", comment);
        model.addAttribute("bookId", bookId);
        return "comment/edit";
    }

    // Actualizar una reseña
    @PostMapping("/{commentId}/update")
    public String updateComment(@PathVariable Long bookId, @PathVariable Long commentId,
                                @RequestParam("name") String name,
                                @RequestParam("description") String description) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setName(name);
        comment.setDescription(description);
        commentRepository.save(comment);
        return "redirect:/books/" + bookId;
    }

    // Eliminar una reseña
    @PostMapping("/{commentId}/delete")
    public String deleteComment(@PathVariable Long bookId, @PathVariable Long commentId) {
        commentRepository.deleteById(commentId);
        return "redirect:/books/" + bookId;
    }
}
