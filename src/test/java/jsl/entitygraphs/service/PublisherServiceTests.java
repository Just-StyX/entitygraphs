package jsl.entitygraphs.service;

import jsl.entitygraphs.entities.Author;
import jsl.entitygraphs.entities.Book;
import jsl.entitygraphs.entities.Publisher;
import jsl.entitygraphs.repository.AuthorRepository;
import jsl.entitygraphs.repository.PublisherRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTests {
    @Mock
    private PublisherRepository publisherRepository;
    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private PublisherServices publisherServices;

    private final Publisher publisher = new Publisher();
    private final Author author = new Author();

    @Test
    @DisplayName("creating a publisher")
    public void createPublisher() {
        when(publisherRepository.save(any(Publisher.class))).thenReturn(any(Publisher.class));

        var savedPublisher = publisherServices.savePublisher(publisher);
        verify(publisherRepository).save(any(Publisher.class));
    }

    @Test
    @DisplayName("testing add book method")
    public void addingBook() {
        when(publisherRepository.findById(anyString())).thenReturn(Optional.of(publisher));
        when(authorRepository.findById(anyString())).thenReturn(Optional.of(author));

        var oldPublisher = publisherServices.addBook("abc", "qwe", new Book());
        InOrder order = inOrder(publisherRepository, authorRepository);
        order.verify(authorRepository).findById(anyString());
        order.verify(publisherRepository).findById(anyString());
        order.verify(publisherRepository).save(any(Publisher.class));
    }
}
