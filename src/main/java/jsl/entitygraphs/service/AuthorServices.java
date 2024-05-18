package jsl.entitygraphs.service;

import jsl.entitygraphs.entities.Author;
import jsl.entitygraphs.entities.Book;
import jsl.entitygraphs.repository.AuthorRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServices {
    private final AuthorRepository authorRepository;

    public AuthorServices(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }
    public List<Author> findAllAuthors(int pageNumber, int pageSize) {
        Sort.TypedSort<Author> sort = Sort.sort(Author.class);
        Sort orders = sort.by(Author::getName);
        return authorRepository.findAll(PageRequest.of(pageNumber, pageSize, orders)).getContent();
    }
}
