package jsl.entitygraphs.repository;

import jsl.entitygraphs.GetLocation;
import jsl.entitygraphs.entities.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTests {
    @Autowired
    private AuthorRepository authorRepository;

    private final Author author = new Author();

    @Container
    @ServiceConnection
    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @BeforeEach
    public void setAuthor() {
        author.setId(null);
        author.setName("Jon Wills");
        author.setInstitution("St Powell");
        author.setLocation(GetLocation.location);
    }

    @Test
    @DisplayName("save author to database")
    public void saveAuthor() {
        var savedAuthor = authorRepository.save(author);

        assertAll(
                () -> assertNotNull(savedAuthor.getId()),
                () -> {
                    Optional<Author> byId = authorRepository.findById(savedAuthor.getId());
                    assertTrue(byId.isPresent());
                },
                () -> {
                    savedAuthor.setName("updated name");
                    var updatedAuthor = authorRepository.save(savedAuthor);
                    assertAll(
                            () -> assertEquals(updatedAuthor.getId(), savedAuthor.getId()),
                            () -> assertThat("updated name").isEqualTo(updatedAuthor.getName())
                    );
                },
                () -> {
                    authorRepository.delete(savedAuthor);
                    assertThat(authorRepository.findById(savedAuthor.getId())).isEmpty();
                }
        );

    }
}
