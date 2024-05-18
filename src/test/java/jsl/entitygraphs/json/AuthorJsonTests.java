package jsl.entitygraphs.json;

import jsl.entitygraphs.GetLocation;
import jsl.entitygraphs.entities.Author;
import jsl.entitygraphs.entities.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class AuthorJsonTests {
    private final JacksonTester<Author> authorJacksonTester;
    private final Author author = new Author();

    @Autowired
    public AuthorJsonTests(JacksonTester<Author> authorJacksonTester) {
        this.authorJacksonTester = authorJacksonTester;
    }

    @BeforeEach
    public void setAuthor() {
        author.setId(null);
        author.setName("Jon Wills");
        author.setInstitution("St Powell");
        author.setLocation(GetLocation.location);
    }

    @Test
    @DisplayName("Serializing author instance")
    public void serializingAuthor() throws IOException {
        var authorSerialize = authorJacksonTester.write(author);
        assertThat(authorSerialize).isEqualToJson("author.json");
    }

    @Test
    @DisplayName("Deserializing author json")
    public void deserializingAuthorJson() throws IOException {
        var expectedAuthorJson = """
                {
                  "name":"Jon Wills",
                  "institution":"St Powell",
                  "location":{
                    "email":"wills@email.com",
                    "country":"United State",
                    "state":"Georgia",
                    "city":"Atlanta",
                    "street":"123 Mountain View"
                  }
                }
                """;
        assertThat(authorJacksonTester.parse(expectedAuthorJson)).extracting(Object::toString).isEqualTo(author.toString());
    }
}
