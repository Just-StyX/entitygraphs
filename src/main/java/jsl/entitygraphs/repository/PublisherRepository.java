package jsl.entitygraphs.repository;

import jsl.entitygraphs.entities.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, String> {
    @EntityGraph(
            attributePaths = {"books.authors"},
            type = EntityGraph.EntityGraphType.FETCH
    )
    public Page<Publisher> findAll(Pageable pageable);
    Page<Publisher> findAll(Specification<Publisher> specification, Pageable pageable);
}
