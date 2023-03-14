package com.javatemplate.fakes;

import com.javatemplate.domain.book.Book;
import com.javatemplate.persistent.book.BookEntity;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@UtilityClass
public class BookFakes {

    public static Book buildBook() {
        return Book.builder()
                .id(randomUUID())
                .name(randomAlphabetic(3, 10))
                .author(randomAlphabetic(3, 10))
                .description(randomAlphabetic(3, 10))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .image(randomAlphabetic(3, 10))
                .userId(randomUUID())
                .build();
    }

    public static List<Book> buildBooks() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildBook())
                .toList();
    }

    public static BookEntity buildBookEntity() {
        return BookEntity.builder()
                .id(randomUUID())
                .name(randomAlphabetic(3, 10))
                .author(randomAlphabetic(3, 10))
                .description(randomAlphabetic(3, 10))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .image(randomAlphabetic(3, 10))
                .userId(randomUUID())
                .build();
    }

    public static List<BookEntity> buildBookEntities() {
        return IntStream.range(1, 5)
                .mapToObj(_ignored -> buildBookEntity())
                .toList();
    }
}
