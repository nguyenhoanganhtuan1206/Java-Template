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
import static org.assertj.core.util.Lists.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    void shouldSave_OK() {
        final var book = buildBookEntity();

        when(bookRepository.save(any())).thenReturn(book);

        final var actual = bookStore.save(toBook(book));

        assertEquals(book.getId(), actual.getId());
        assertEquals(book.getName(), actual.getName());
        assertEquals(book.getAuthor(), actual.getAuthor());
        assertEquals(book.getUpdatedAt(), actual.getUpdatedAt());
        assertEquals(book.getCreatedAt(), actual.getCreatedAt());
        assertEquals(book.getDescription(), actual.getDescription());
        assertEquals(book.getImage(), actual.getImage());
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


        assertEquals(actual.getId(), expected.getId());
        assertEquals(actual.getName(), expected.getName());
        assertEquals(actual.getAuthor(), expected.getAuthor());
        assertEquals(actual.getUpdatedAt(), expected.getUpdatedAt());
        assertEquals(actual.getCreatedAt(), expected.getCreatedAt());
        assertEquals(actual.getDescription(), expected.getDescription());
        assertEquals(actual.getImage(), expected.getImage());

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
    void shouldBooksFindByName_OK() {
        final var book = buildBookEntity();
        final var expected = buildBookEntities();

        when(bookRepository.findByNameAuthorDesc(anyString())).thenReturn(expected);

        final var actual = bookStore.findByNameAuthorDesc(book.getName());

        assertEquals(actual.size(), expected.size());

        verify(bookRepository).findByNameAuthorDesc(book.getName());
    }

    @Test
    void shouldBooksFindByName_Empty() {
        final var bookName = randomAlphabetic(3, 10);

        when(bookRepository.findByNameAuthorDesc(bookName)).thenReturn(emptyList());

        final var actual = bookStore.findByNameAuthorDesc(bookName);

        assertTrue(actual.isEmpty());

        verify(bookRepository).findByNameAuthorDesc(bookName);
    }
}
