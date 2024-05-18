package jsl.entitygraphs.repository;

import jsl.entitygraphs.entities.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, String> {
    @Override
    @EntityGraph(
            attributePaths = {"books.publisher"},
            type = EntityGraph.EntityGraphType.FETCH
    )
    public Page<Author> findAll(Pageable pageable);
}
