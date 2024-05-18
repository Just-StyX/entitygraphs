package jsl.entitygraphs.repository;

import jsl.entitygraphs.entities.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String>, JpaSpecificationExecutor<Book> {
    @Query("""
            select b from Book b where b.numberOfPages > :lower and b.numberOfPages < :high
            """)
    public Page<Book> findAllNumberOfPagesBetween(
            Pageable pageable,
            @Param("lower") int lower,
            @Param("high") int high
    );

}
