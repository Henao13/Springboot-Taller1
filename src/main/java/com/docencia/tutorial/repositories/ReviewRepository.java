package com.docencia.tutorial.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.docencia.tutorial.models.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
