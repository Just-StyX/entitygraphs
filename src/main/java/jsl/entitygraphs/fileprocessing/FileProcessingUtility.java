package jsl.entitygraphs.fileprocessing;

import jsl.entitygraphs.entities.*;
import jsl.entitygraphs.exceptions.InvalidStringPathException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public record FileProcessingUtility(Path locationPath, Charset charset, String delimiter) {
    public static List<Location> getLocations;
    public FileProcessingUtility(Path locationPath){
        this(locationPath, StandardCharsets.UTF_8, ",");
        getLocations = processLocations(locationPath());
    }
    public List<Location> processLocations(Path locationPath) {
        List<Location> content = new ArrayList<>();
        try {
            assert charset() != null;
            try (BufferedReader bufferedReader = Files.newBufferedReader(locationPath, charset())) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    assert delimiter() != null;
                    String[] values = line.split(Pattern.quote(delimiter()));
                    String[] nonNullValues = new String[values.length];
                    for (int i = 0; i < values.length; i++) {
                        nonNullValues[i] = values[i] != null ? values[i] : "empty values";
                    }
                    content.add(new Location(nonNullValues[0], nonNullValues[1], nonNullValues[2], nonNullValues[3], nonNullValues[4]));
                }
            }
        } catch (IOException exception) {
            throw new InvalidStringPathException("Invalid file path");
        }
        return content;
    }

    public List<Author> processAuthor(Path authorPath) {
        List<Author> authors = new ArrayList<>();
        var locations = getLocations;
        try (BufferedReader bufferedReader = Files.newBufferedReader(authorPath, charset())) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(Pattern.quote(delimiter()));
                String[] nonNullValues = new String[values.length];
                for (int i = 0; i < values.length; i++) {
                    nonNullValues[i] = values[i] != null ? values[i] : "empty values";
                }
                var author = new Author();
                author.setName(nonNullValues[0]);
                author.setInstitution(nonNullValues[1]);
                authors.add(author);
            }
        } catch (IOException exception) {
            throw new InvalidStringPathException("Invalid file path");
        }
        List<Author> processedAuthors = new ArrayList<>();
        for (int i = 0; i < authors.size(); i++) {
            var processAuthor = authors.get(i);
            processAuthor.setLocation(locations.get(i));
            processedAuthors.add(processAuthor);
        }
        return processedAuthors;
    }

    public List<Publisher> processPublisher(Path publisherPath) {
        List<Publisher> publishers = new ArrayList<>();
        var locations = getLocations;
        try (BufferedReader bufferedReader = Files.newBufferedReader(publisherPath, charset())) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                var publisher = new Publisher();
                publisher.setPublisherName(line);
                publishers.add(publisher);
            }
        } catch (IOException exception) {
            throw new InvalidStringPathException("Invalid file path");
        }
        List<Publisher> processedPublishers = new ArrayList<>();
        for (int i = 0; i < publishers.size(); i++) {
            var processedPublisher = publishers.get(i);
            processedPublisher.setLocation(locations.get(i));
            processedPublishers.add(processedPublisher);
        }
        return processedPublishers;
    }

    public List<Review> processReview(Path reviewPath) {
        List<Review> reviews = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(reviewPath, charset)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                var review = new Review();
                review.setContent(line);
                reviews.add(review);
            }
        } catch (IOException exception) {
            throw new InvalidStringPathException("Invalid file path");
        }
        return reviews;
    }

    public List<Book> processBooks(Path bookPath) {
        Random rand = new Random();
        List<Book> books = new ArrayList<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(bookPath, charset())) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] values = line.split(Pattern.quote(delimiter()));
                var isbn = values[0] != null ? values[0] : "empty isbn";
                var title = values[1] != null ? values[1] : "empty title";
                var type = values[2] != null ? values[2] : "empty type";
                BookType bookType;
                if (type.equalsIgnoreCase("blue")) bookType = BookType.MAGAZINE;
                else if (type.equalsIgnoreCase("black")) bookType = BookType.ARTICLE;
                else bookType = BookType.TEXT;
                var numberOfPages = !values[3].isEmpty() ? Integer.parseInt(values[3]) : 0;
                var randomMonth = rand.nextInt(1, 12);
                var randomDay = rand.nextInt(1, 29);
                var publishedDate = values[4] != null ? LocalDate.of(Integer.parseInt(values[4]), randomMonth, randomDay) : LocalDate.of(1900, 1, 1);
                var bookVersion = values[5] != null ? Integer.parseInt(values[5]) : 0;
                var book = new Book();
                book.setIsbn(isbn);
                book.setTitle(title);
                book.setBookType(bookType);
                book.setNumberOfPages(numberOfPages);
                book.setPublishDate(publishedDate);
                book.setBookVersion(bookVersion);
                books.add(book);
            }
        } catch (IOException exception) {
            throw new InvalidStringPathException("Invalid file path");
        }
        return books;
    }
}
