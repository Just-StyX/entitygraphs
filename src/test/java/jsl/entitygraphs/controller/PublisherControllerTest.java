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
public class PublisherControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Save some publishers")
    public void savePublisher() throws Exception {
        var publisherJson = """
                {
                  "publisherName":"Spring Mathematics",
                  "location":{
                    "email":"wills@email.com",
                    "country":"United State",
                    "state":"New York",
                    "city":"Brooklyn",
                    "street":"13th Street. Brooklyn Bridge"
                  }
                }
                """;
        var result = mockMvc.perform(post("/api/publisher")
                .contentType(MediaType.APPLICATION_JSON).content(publisherJson));
        result.andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Publish a book")
    public void publishBook() throws Exception {
        var bookJson = """
                {
                  "isbn":"12545XYZ",
                  "title":"The Blue Ocean",
                  "bookType":"MAGAZINE",
                  "numberOfPages":15,
                  "publishDate":"2024-02-01",
                  "bookVersion":1
                }
                """;
        var path = "/api/publisher/352cd60e-d59e-4446-8d49-18ea52179099/0ba62781-c8d2-4cb7-90de-e6fa0d9df3ca";
        var result = mockMvc.perform(post(path)
                .contentType(MediaType.APPLICATION_JSON).content(bookJson));
        result.andExpect(status().isAccepted());
    }
}
