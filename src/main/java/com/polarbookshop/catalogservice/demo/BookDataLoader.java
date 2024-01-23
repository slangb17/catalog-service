package com.polarbookshop.catalogservice.demo;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Profile(value = "test-data")
public class BookDataLoader {

    private final BookRepository bookRepository;

    public BookDataLoader(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    void addTestData() {
        bookRepository.deleteAll();
        Book book1 = Book.of("1234567891", "Title 1", "Author 1", 1.0, "Publisher 1");
        Book book2 = Book.of("1234567892", "Title 2", "Author 2", 2.0, "Publisher 2");
        bookRepository.saveAll(List.of(book1, book2));
    }
}
