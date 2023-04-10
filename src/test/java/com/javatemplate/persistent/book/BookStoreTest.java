package com.javatemplate.persistent.book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.javatemplate.fakes.BookFakes.buildBookEntities;
import static com.javatemplate.fakes.BookFakes.buildBookEntity;
import static com.javatemplate.persistent.book.BookEntityMapper.toBook;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.util.Lists.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookStoreTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookStore bookStore;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildBookEntities();

        when(bookRepository.findAll())
                .thenReturn(expected);

        final var actual = bookStore.findAll();

        assertEquals(expected.size(), actual.size());

        verify(bookRepository).findAll();
    }

    @Test
    void shouldFindBookByIsbn13_OK() {
        final var book = buildBookEntity();

        when(bookRepository.findByIsbn13(book.getIsbn13())).thenReturn(Optional.of(book));

        final var actual = bookStore.findByIsbn13(book.getIsbn13()).get();
        final var expected = book;

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImage(), actual.getImage());
        assertEquals(expected.getSubtitle(), actual.getSubtitle());
        assertEquals(expected.getPublisher(), actual.getPublisher());
        assertEquals(expected.getIsbn13(), actual.getIsbn13());
        assertEquals(expected.getPrice(), actual.getPrice());
        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getRating(), actual.getRating());
        assertEquals(expected.getUserId(), actual.getUserId());

        verify(bookRepository).findByIsbn13(book.getIsbn13());
    }

    @Test
    void shouldFindBookByIsbn13_Empty() {
        final var isbn13 = randomNumeric(13);

        when(bookRepository.findByIsbn13(isbn13)).thenReturn(Optional.empty());

        assertFalse(bookStore.findByIsbn13(isbn13).isPresent());
        verify(bookRepository).findByIsbn13(isbn13);
    }

    @Test
    void shouldSave_OK() {
        final var bookSave = buildBookEntity();

        when(bookRepository.save(any())).thenReturn(bookSave);

        final var actual = bookStore.save(toBook(bookSave));

        assertEquals(bookSave.getId().toString(), actual.getId().toString());
        assertEquals(bookSave.getName(), actual.getName());
        assertEquals(bookSave.getAuthor(), actual.getAuthor());
        assertEquals(bookSave.getDescription(), actual.getDescription());
        assertEquals(bookSave.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(bookSave.getImage(), actual.getImage());
        assertEquals(bookSave.getSubtitle(), actual.getSubtitle());
        assertEquals(bookSave.getPublisher(), actual.getPublisher());
        assertEquals(bookSave.getIsbn13(), actual.getIsbn13());
        assertEquals(bookSave.getPrice(), actual.getPrice());
        assertEquals(bookSave.getYear(), actual.getYear());
        assertEquals(bookSave.getRating(), actual.getRating());
        assertEquals(bookSave.getUserId(), actual.getUserId());

        verify(bookRepository).save(any());
    }

    @Test
    void shouldDeleteById_OK() {
        final var book = buildBookEntity();

        bookStore.deleteById(book.getId());

        verify(bookRepository).deleteById(book.getId());
    }

    @Test
    void shouldFindById_OK() {
        final var book = buildBookEntity();
        final var bookOptional = Optional.of(book);

        when(bookRepository.findById(book.getId())).thenReturn(bookOptional);

        final var actual = bookStore.findById(book.getId()).get();
        final var expected = bookOptional.get();

        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getAuthor(), actual.getAuthor());
        assertEquals(expected.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(expected.getCreatedAt(), actual.getCreatedAt());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getImage(), actual.getImage());

        verify(bookRepository).findById(book.getId());
    }

    @Test
    void shouldFindById_Empty() {
        final var uuid = randomUUID();

        when(bookRepository.findById(uuid)).thenReturn(Optional.empty());

        assertFalse(bookStore.findById(uuid).isPresent());
        verify(bookRepository).findById(uuid);
    }

    @Test
    void findByNameAuthorDescription_OK() {
        final var book = buildBookEntity();
        final var expected = buildBookEntities();

        when(bookRepository.find(book.getName())).thenReturn(expected);

        final var actual = bookStore.find(book.getName());

        assertEquals(expected.size(), actual.size());

        verify(bookRepository).find(book.getName());
    }

    @Test
    void findByNameAuthorDescription_Empty() {
        final var bookName = randomAlphabetic(3, 10);

        when(bookRepository.find(bookName)).thenReturn(emptyList());

        final var actual = bookStore.find(bookName);

        assertTrue(actual.isEmpty());

        verify(bookRepository).find(bookName);
    }
}
