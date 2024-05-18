package jsl.entitygraphs.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Save some authors")
    public void saveAuthor() throws Exception {
        var authorJson = """
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
        var result = mockMvc.perform(post("/api/author")
                .contentType(MediaType.APPLICATION_JSON).content(authorJson));
        result.andExpect(status().isCreated());
    }
}
