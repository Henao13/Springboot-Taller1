package com.docencia.tutorial.controllers;

import com.docencia.tutorial.models.Product;
import com.docencia.tutorial.models.Comment;
import com.docencia.tutorial.repositories.ProductRepository;
import com.docencia.tutorial.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping("/products")
    public String index(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("title", "Products - Online Store");
        model.addAttribute("subtitle", "List of products");
        model.addAttribute("products", products);
        return "product/index"; // Vista: product/index.html
    }

    @GetMapping("/products/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("title", product.getName() + " - Online Store");
        model.addAttribute("subtitle", product.getName() + " - Product information");
        model.addAttribute("product", product);
        return "product/show"; // Vista: product/show.html
    }

    @GetMapping("/products/create")
    public String createProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product/create";
    }

    @PostMapping("/products")
    public String save(Product product) {
        if (product.getName() == null || product.getName().isEmpty() || product.getPrice() == null) {
            throw new RuntimeException("Name and Price are required");
        }
        productRepository.save(product);
        return "redirect:/products";
    }

    @PostMapping("/products/{id}/comments")
    public String addComment(@PathVariable("id") Long id, @RequestParam("description") String description) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        Comment comment = new Comment(description, product);
        commentRepository.save(comment);
        return "redirect:/products/" + id;
    }
}
