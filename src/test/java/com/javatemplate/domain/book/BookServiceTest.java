package com.javatemplate.domain.book;

import com.javatemplate.error.NotFoundException;
import com.javatemplate.persistent.book.BookStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.javatemplate.fakes.BookFakes.buildBook;
import static com.javatemplate.fakes.BookFakes.buildBooks;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookStore bookStore;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildBooks();

        when(bookStore.findAll())
                .thenReturn(expected);

        final var actual = bookService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());

        verify(bookStore).findAll();
    }

    @Test
    void shouldFindById_Ok() {
        final var book = buildBook();

        when(bookStore.findById(book.getId()))
                .thenReturn(Optional.of(book));

        assertEquals(book, bookService.findById(book.getId()));
        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldFindById_Thrown() {
        final var uuid = randomUUID();

        when(bookStore.findById(uuid))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findById(uuid));
        verify(bookStore).findById(uuid);
    }


    @Test
    void shouldCreate_Ok() {
        final var book = buildBook();

        when(bookStore.save(book)).thenReturn(book);

        assertEquals(book, bookService.create(book));
        verify(bookStore).save(book);
    }

    @Test
    void shouldCreate_Thrown() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));

//        assertThrows(book, bookService.create(book));
        verify(bookStore).save(book);
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}
