package jsl.entitygraphs.controller;

import jsl.entitygraphs.entities.Book;
import jsl.entitygraphs.entities.Review;
import jsl.entitygraphs.service.BookServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {
    private final BookServices bookServices;

    public BookController(BookServices bookServices) {
        this.bookServices = bookServices;
    }

    @PostMapping("/{bookId}")
    private ResponseEntity<Book> addReview(@PathVariable String bookId, @RequestBody Review review) {
        var book = bookServices.addReview(bookId, review);
        return ResponseEntity.accepted().body(book);
    }

    @GetMapping
    private ResponseEntity<List<Book>> findBetween(
            @RequestParam int lower,
            @RequestParam int high,
            @RequestParam int page,
            @RequestParam int size) {
        var books = bookServices.findAllBooksWithPagesBetweenXAndY(lower, high, page, size);
        return ResponseEntity.ok(books);
    }
    @GetMapping("/all")
    private ResponseEntity<List<Book>> findAll(
            @RequestParam int lower,
            @RequestParam int high,
            @RequestParam int page,
            @RequestParam int size
    ) {
        var books = bookServices.findAllBooks(lower, high, page, size);
        return ResponseEntity.ok(books);
    }
}
