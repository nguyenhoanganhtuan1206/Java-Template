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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void shouldSave_OK() {
        final var book = buildBookEntity();

        when(bookRepository.save(any())).thenReturn(book);

        final var expected = bookStore.save(toBook(book));

        assertEquals(book.getId(), expected.getId());
        assertEquals(book.getName(), expected.getName());
        assertEquals(book.getAuthor(), expected.getAuthor());
        assertEquals(book.getUpdatedAt(), expected.getUpdatedAt());
        assertEquals(book.getCreatedAt(), expected.getCreatedAt());
        assertEquals(book.getDescription(), expected.getDescription());
        assertEquals(book.getImage(), expected.getImage());
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
        final var bookOpt = Optional.of(book);

        when(bookRepository.findById(book.getId())).thenReturn(bookOpt);

        final var actual = bookStore.findById(book.getId()).get();
        final var expected = bookOpt.get();


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
}
