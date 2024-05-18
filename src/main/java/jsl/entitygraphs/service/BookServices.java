package jsl.entitygraphs.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jsl.entitygraphs.entities.Book;
import jsl.entitygraphs.entities.Review;
import jsl.entitygraphs.repository.BookRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServices {
    private final BookRepository bookRepository;

    public BookServices(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addReview(String bookId, Review review) {
        var book = bookRepository.findById(bookId);
        if (book.isPresent()) {
            var foundBook = book.get();
            foundBook.addReview(review);
            return bookRepository.save(foundBook);
        }
        return null;
    }

    public List<Book> findAllBooks(int lower, int high, int pageNumber, int pageSize) {
        Sort.TypedSort<Book> sort = Sort.sort(Book.class);
        Sort orders = sort.by(Book::getBookVersion);
        return bookRepository.findAll(pageBetweenXAndY(lower, high), PageRequest.of(pageNumber, pageSize, orders)).getContent();
    }

    public List<Book> findAllBooksWithPagesBetweenXAndY(int lower, int high, int pageNumber, int pageSize) {
        Sort.TypedSort<Book> sort = Sort.sort(Book.class);
        Sort orders = sort.by(Book::getBookVersion);
        return bookRepository
                .findAllNumberOfPagesBetween(PageRequest.of(pageNumber, pageSize, orders), lower, high).getContent();
    }

    private Specification<Book> pageBetweenXAndY(int lower, int high) {
        Specification<Book> greaterThanX = (Root<Book> root,
                CriteriaQuery<?> query, CriteriaBuilder builder) -> builder
                    .greaterThanOrEqualTo(root.get("numberOfPages"), lower);
        Specification<Book> lessThanY = (Root<Book> root,
                                            CriteriaQuery<?> query, CriteriaBuilder builder) -> builder
                    .lessThanOrEqualTo(root.get("numberOfPages"), high);

        return greaterThanX.and(lessThanY);
    }
}
