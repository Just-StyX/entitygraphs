package jsl.entitygraphs.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name = "book_review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "content", nullable = false)
    private String content;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Review review = (Review) object;
        return Objects.equals(id, review.id) && Objects.equals(content, review.content) && Objects.equals(book, review.book);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, book);
    }

    @Override
    public String toString() {
        return "Review{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
