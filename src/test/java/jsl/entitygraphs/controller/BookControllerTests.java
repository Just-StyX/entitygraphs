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
public class BookControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Save some reviews")
    public void saveReview() throws Exception {
        var reviewJson = """
                {
                  "content":"ipsum ipsum ipsum lorem lorem"
                }
                """;
        var result = mockMvc.perform(post("/api/book/858b5ec5-deac-4cc2-94cb-ab28c3747833")
                .contentType(MediaType.APPLICATION_JSON).content(reviewJson));
        result.andExpect(status().isAccepted());
    }
}
