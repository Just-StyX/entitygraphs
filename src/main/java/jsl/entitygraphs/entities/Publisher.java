package jsl.entitygraphs.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "publisher")
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "publisher_name", nullable = false)
    private String publisherName;

    @Embedded
    private Location location;

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "publisher")
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        this.books.add(book);
        book.setPublisher(this);
    }

    public void removeBook(Book book) {
        book.setPublisher(null);
        this.books.remove(book);
    }

    public void removeBooks() {
        Iterator<Book> bookIterator = this.books.iterator();
        while (bookIterator.hasNext()) {
            Book book = bookIterator.next();
            book.setPublisher(null);
            bookIterator.remove();
        }
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "id='" + id + '\'' +
                ", publisherName='" + publisherName + '\'' +
                ", location=" + location +
                '}';
    }
}
