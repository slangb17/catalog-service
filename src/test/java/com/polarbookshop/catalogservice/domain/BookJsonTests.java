package com.polarbookshop.catalogservice.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    @Test
    void testSerialize() throws Exception {
        Book book = Book.of("1234567890", "Title", "Author", 9.34, "Publisher");

        JsonContent<Book> jsonContent = json.write(book);
        assertThat(jsonContent)
                .extractingJsonPathStringValue("@.isbn").isEqualTo(book.isbn());
        assertThat(jsonContent)
                .extractingJsonPathStringValue("@.title").isEqualTo(book.title());
        assertThat(jsonContent)
                .extractingJsonPathStringValue("@.author").isEqualTo(book.author());
        assertThat(jsonContent)
                .extractingJsonPathNumberValue("@.price").isEqualTo(book.price());
        assertThat(jsonContent)
                .extractingJsonPathStringValue("@.publisher").isEqualTo(book.publisher());
    }

    @SuppressWarnings("AssertBetweenInconvertibleTypes")
    @Test
    void testDeserialize() throws IOException {
        String content = """
            {
            "isbn": "1234567890",
            "title": "Title",
            "author": "Author",
            "price": 12.03,
            "publisher": "Publisher"
            }
            """;

        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(Book.of("1234567890", "Title", "Author", 12.03, "Publisher"));
    }
}
