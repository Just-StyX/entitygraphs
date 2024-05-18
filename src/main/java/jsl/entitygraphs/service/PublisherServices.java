package jsl.entitygraphs.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jsl.entitygraphs.entities.Book;
import jsl.entitygraphs.entities.Publisher;
import jsl.entitygraphs.repository.AuthorRepository;
import jsl.entitygraphs.repository.PublisherRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublisherServices {
    private final PublisherRepository publisherRepository;
    private final AuthorRepository authorRepository;

    public PublisherServices(PublisherRepository publisherRepository, AuthorRepository authorRepository) {
        this.publisherRepository = publisherRepository;
        this.authorRepository = authorRepository;
    }

    public Publisher savePublisher(Publisher publisher) {
        return publisherRepository.save(publisher);
    }

    public List<Publisher> findAllPublishers(int page, int size) {
        Sort.TypedSort<Publisher> publisherTypedSort = Sort.sort(Publisher.class);
        Sort sort = publisherTypedSort.by(Publisher::getPublisherName);
        return publisherRepository.findAll(PageRequest.of(page, size, sort)).getContent();
    }

    public Publisher addBook(String publisherId, String authorId, Book book) {
        var author = authorRepository.findById(authorId);
        var publisher = publisherRepository.findById(publisherId);
        if (publisher.isPresent() && author.isPresent()) {
            var foundAuthor = author.get();
            var foundPublisher = publisher.get();
            foundAuthor.addBook(book);
            foundPublisher.addBook(book);
            return publisherRepository.save(foundPublisher);
        }
        return null;
    }

    public List<Publisher> findPublishersNameLessThanX(int len, int page, int size) {
        Sort.TypedSort<Publisher> publisherTypedSort = Sort.sort(Publisher.class);
        Sort sort = publisherTypedSort.by(Publisher::getPublisherName);
        return publisherRepository.findAll(getNamesLessThanX(len), PageRequest.of(page, size, sort)).getContent();
    }

    private Specification<Publisher> getNamesLessThanX(int len) {
        return (Root<Publisher> root,CriteriaQuery<?> query,
                CriteriaBuilder builder) -> builder.lessThanOrEqualTo(root.get("publisherName"), len);
    }
}
