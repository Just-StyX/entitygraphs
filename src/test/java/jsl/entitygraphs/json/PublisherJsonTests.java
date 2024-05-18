package jsl.entitygraphs.json;

import jsl.entitygraphs.GetLocation;
import jsl.entitygraphs.entities.Book;
import jsl.entitygraphs.entities.BookType;
import jsl.entitygraphs.entities.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class PublisherJsonTests {
    private final JacksonTester<Publisher> publisherJacksonTester;
    private final Publisher publisher = new Publisher();
    private final Publisher publishedBooks = new Publisher();

    @Autowired
    public PublisherJsonTests(JacksonTester<Publisher> publisherJacksonTester) {
        this.publisherJacksonTester = publisherJacksonTester;
    }

    @BeforeEach
    public void setPublisher() {
        publisher.setId(null);
        publisher.setPublisherName("Spring Mathematics");
        publisher.setLocation(GetLocation.location);

        var book = new Book();
        book.setId(null); book.setIsbn("12545XYZ"); book.setTitle("The Blue Ocean"); book.setBookType(BookType.MAGAZINE);
        book.setNumberOfPages(15);
        book.setPublishDate(LocalDate.of(2024, 2, 1));
        book.setBookVersion(1);

        publishedBooks.setId(null);
        publishedBooks.setPublisherName("Spring Mathematics");
        publishedBooks.setLocation(GetLocation.location);
        publishedBooks.addBook(book);
    }

    @Test
    @DisplayName("Serializing publisher")
    public void serializePublisher() throws IOException {
        var serializingPublisher = publisherJacksonTester.write(publisher);
        assertThat(serializingPublisher).isEqualToJson("publisher.json");
    }

    @Test
    @DisplayName("Serialize when books are added")
    public void addingBooks() throws IOException {
        var serializedPublisher = publisherJacksonTester.write(publishedBooks);
        assertThat(serializedPublisher).isEqualToJson("publishedBooks.json");
    }

    @Test
    @DisplayName("Deserializing publisher")
    public void deserializePublisher() throws IOException {
        var publisherJson = """
                {
                  "publisherName":"Spring Mathematics",
                  "location":{
                    "email":"wills@email.com",
                    "country":"United State",
                    "state":"Georgia",
                    "city":"Atlanta",
                    "street":"123 Mountain View"
                  }
                }
                """;
        assertThat(publisherJacksonTester.parse(publisherJson)).extracting(Object::toString).isEqualTo(publisher.toString());
    }

    @Test
    @DisplayName("Deserializing published books")
    public void deserializingPublishedBooks() throws IOException {
        var publishedBooksJson = """
                {
                  "publisherName":"Spring Mathematics",
                  "location":{
                    "email":"wills@email.com",
                    "country":"United State",
                    "state":"Georgia",
                    "city":"Atlanta",
                    "street":"123 Mountain View"
                  },
                  "books": [
                    {
                      "isbn":"12545XYZ",
                      "title":"The Blue Ocean",
                      "bookType":"MAGAZINE",
                      "numberOfPages":15,
                      "publishDate":"2024-02-01",
                      "bookVersion":1
                    }
                  ]
                }
                """;
        assertThat(publisherJacksonTester.parse(publishedBooksJson)).extracting(Object::toString).isEqualTo(publishedBooks.toString());
    }
}
