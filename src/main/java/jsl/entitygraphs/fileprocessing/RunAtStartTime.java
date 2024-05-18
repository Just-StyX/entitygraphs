package jsl.entitygraphs.fileprocessing;

import jsl.entitygraphs.entities.Author;
import jsl.entitygraphs.entities.Publisher;
import jsl.entitygraphs.service.AuthorServices;
import jsl.entitygraphs.service.BookServices;
import jsl.entitygraphs.service.PublisherServices;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//@Component
public class RunAtStartTime implements ApplicationRunner {
   private final AuthorServices authorServices;
   private final PublisherServices publisherServices;
   private final BookServices bookServices;

   public RunAtStartTime(AuthorServices authorServices, PublisherServices publisherServices, BookServices bookServices) {
       this.authorServices = authorServices;
       this.publisherServices = publisherServices;
       this.bookServices = bookServices;
   }

    @Override
    public void run(ApplicationArguments args) throws Exception {
       var path = Paths.get("location.csv");
       var authorPath = Paths.get("author.csv");
       var publisherPath = Paths.get("publisher.csv");
       var bookPath = Paths.get("book.csv");
       var reviewPath = Paths.get("review.csv");
       var process = new FileProcessingUtility(path);

       var authors = process.processAuthor(authorPath);
       var publishers = process.processPublisher(publisherPath);
       var books = process.processBooks(bookPath);
       var reviews = process.processReview(reviewPath);

       List<Author> savedAuthors = new ArrayList<>();
       List<Publisher> savedPublishers = new ArrayList<>();

       for (Author author: authors) savedAuthors.add(authorServices.saveAuthor(author));
       for (Publisher publisher: publishers) savedPublishers.add(publisherServices.savePublisher(publisher));

       for (int i = 0; i < savedAuthors.size(); i++) {
           publisherServices.addBook(savedPublishers.get(i).getId(), savedAuthors.get(i).getId(), books.get(i));
       }

       var savedBooks = bookServices.findAllBooks();
       for (int i = 0; i < savedBooks.size(); i++) {
           bookServices.addReview(savedBooks.get(i).getId(), reviews.get(i));
       }
    }
}
