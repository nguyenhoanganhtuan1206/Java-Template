package com.javatemplate.persistent.book;

import com.javatemplate.domain.book.Book;
import lombok.experimental.UtilityClass;

import java.util.List;

import static org.apache.commons.collections4.ListUtils.emptyIfNull;

@UtilityClass
public class BookEntityMapper {

    public static Book toBook(final BookEntity bookEntity) {
        return Book.builder()
                .id(bookEntity.getId())
                .name(bookEntity.getName())
                .author(bookEntity.getAuthor())
                .description(bookEntity.getDescription())
                .image(bookEntity.getImage())
                .userId(bookEntity.getUserId())
                .createdAt(bookEntity.getCreatedAt())
                .updatedAt(bookEntity.getUpdatedAt())
                .build();
    }

    public static List<Book> toBooks(final List<BookEntity> bookEntities) {
        return emptyIfNull(bookEntities).stream()
                .map(BookEntityMapper::toBook)
                .toList();
    }

    public static BookEntity toBookEntity(final Book book) {
        return BookEntity.builder()
                .id(book.getId())
                .name(book.getName())
                .author(book.getAuthor())
                .description(book.getDescription())
                .image(book.getImage())
                .userId(book.getUserId())
                .createdAt(book.getCreatedAt())
                .updatedAt(book.getUpdatedAt())
                .build();
    }
}
