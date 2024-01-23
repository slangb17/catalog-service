package com.polarbookshop.catalogservice.domain;

import com.polarbookshop.catalogservice.domain.Book;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class BookValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidationSuccessWhenAllFieldsAreSetCorrectly() {
        Book book = Book.of("1234567890", "testTitle", "testAuthor", 10.0, "Publisher");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).isEmpty();
    }

    @Test
    void testThatIncorrectIsbnFailsTheValidation() {
        Book book = Book.of("INVALID_ISBN", "testTitle", "testAuthor", 10.0, "Publisher");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The ISBN must be valid. Either ISBN-10 or ISBN-13.");
    }

    @Test
    void testThatValidationFailsIfPriceIsNegative() {
        Book book = Book.of("1234567890", "testTitle", "testAuthor", -1.0, "Publisher");
        Set<ConstraintViolation<Book>> violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage())
                .isEqualTo("The book price must be greater than zero.");
    }
}
