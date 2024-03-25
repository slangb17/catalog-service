package com.polarbookshop.catalogservice.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    private BookService bookService;

    @BeforeEach
    void setUp() {
        bookService = new BookService(bookRepository);
    }

    @Test
    void shouldReturnBookWhenBookIsFound() {
        String isbn = "123456789";
        // given
        Book book = Book.of(isbn, "Title", "Author", 12.34, "Publisher");
        given(bookRepository.findByIsbn(isbn)).willReturn(java.util.Optional.of(book));

        // when
        Book foundBook = bookService.viewBookDetails(isbn);

        // then
        assertThat(foundBook).isEqualTo(book);
        verify(bookRepository).findByIsbn(isbn);
    }

    @Test
    void shouldThrowBookNotFoundExceptionWhenBookIsNotFound() {
        String isbn = "123456789";
        // given
        given(bookRepository.findByIsbn(isbn)).willReturn(java.util.Optional.empty());

        // when and then
        assertThatThrownBy(() -> bookService.viewBookDetails(isbn))
               .isInstanceOf(BookNotFoundException.class)
               .hasMessage("The book with ISBN " + isbn + " was not found.");
        verify(bookRepository).findByIsbn(isbn);
    }
}