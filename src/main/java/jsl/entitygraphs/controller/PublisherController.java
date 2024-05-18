package jsl.entitygraphs.controller;

import jsl.entitygraphs.entities.Book;
import jsl.entitygraphs.entities.Publisher;
import jsl.entitygraphs.service.PublisherServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/publisher")
public class PublisherController {
    private final PublisherServices publisherServices;

    public PublisherController(PublisherServices publisherServices) {
        this.publisherServices = publisherServices;
    }

    @PostMapping
    private ResponseEntity<Void> createPublisher(@RequestBody Publisher publisher,
                                                 UriComponentsBuilder uriComponentsBuilder) {
        var savedPublisher = publisherServices.savePublisher(publisher);
        URI location = uriComponentsBuilder.path("/api/publisher/{id}")
                .buildAndExpand(savedPublisher.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{authorId}/{publisherId}")
    private ResponseEntity<Void> addBook(@PathVariable String authorId, @PathVariable String publisherId,
                                         @RequestBody Book book) {
        var publisher = publisherServices.addBook(publisherId, authorId, book);
        return ResponseEntity.accepted().build();
    }

    @GetMapping
    private ResponseEntity<List<Publisher>> getAll(@RequestParam int page, @RequestParam int size) {
        var publishers = publisherServices.findAllPublishers(page, size);
        return ResponseEntity.ok(publishers);
    }

    @GetMapping("/length")
    private ResponseEntity<List<Publisher>> getNamesLessThanX(
            @RequestParam int page, @RequestParam int size, @RequestParam int len) {
        var publishers = publisherServices.findPublishersNameLessThanX(len, page, size);
        return ResponseEntity.ok(publishers);
    }
}
