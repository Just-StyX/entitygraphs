package jsl.entitygraphs.repository;

import jsl.entitygraphs.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
