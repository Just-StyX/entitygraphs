package jsl.entitygraphs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private BookType bookType;

    @Column(name = "pages", nullable = false)
    private int numberOfPages;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "book_version", nullable = false)
    private int bookVersion;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "books")
    private Set<Author> authors = new LinkedHashSet<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "book")
    private List<Review> reviews = new ArrayList<>();

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setBook(this);
    }

    public void removeReview(Review review) {
        review.setBook(null);
        this.reviews.remove(review);
    }

    public void removeReview() {
        Iterator<Review> reviewIterator = this.reviews.iterator();
        while (reviewIterator.hasNext()) {
            Review review = reviewIterator.next();
            review.setBook(null);
            reviewIterator.remove();
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Book book = (Book) object;
        return numberOfPages == book.numberOfPages && bookVersion == book.bookVersion && Objects.equals(id, book.id) && Objects.equals(isbn, book.isbn) && Objects.equals(title, book.title) && bookType == book.bookType && Objects.equals(publishDate, book.publishDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, isbn, title, bookType, numberOfPages, publishDate, bookVersion);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", bookType=" + bookType +
                ", numberOfPages=" + numberOfPages +
                ", publishDate=" + publishDate +
                ", bookVersion=" + bookVersion +
                '}';
    }
}
