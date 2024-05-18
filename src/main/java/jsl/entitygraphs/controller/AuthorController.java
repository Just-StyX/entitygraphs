package jsl.entitygraphs.controller;

import jsl.entitygraphs.entities.Author;
import jsl.entitygraphs.service.AuthorServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/author")
public class AuthorController {
    private final AuthorServices authorServices;

    public AuthorController(AuthorServices authorServices) {
        this.authorServices = authorServices;
    }

    @PostMapping
    public ResponseEntity<Void> createAuthor(@RequestBody Author author, UriComponentsBuilder uriComponentsBuilder) {
        var savedAuthor = authorServices.saveAuthor(author);
        URI location = uriComponentsBuilder.path("/api/author/{id}").buildAndExpand(savedAuthor.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    private ResponseEntity<List<Author>> getAuthors(@RequestParam int page, @RequestParam int size) {
        var authors = authorServices.findAllAuthors(page, size);
        return ResponseEntity.ok(authors);
    }
}
