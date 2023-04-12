package com.javatemplate.domain.book;

import com.javatemplate.domain.auth.AuthsProvider;
import com.javatemplate.error.AccessDeniedException;
import com.javatemplate.error.BadRequestException;
import com.javatemplate.error.NotFoundException;
import com.javatemplate.persistent.book.BookStore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.Instant;
import java.util.Optional;

import static com.javatemplate.fakes.BookFakes.buildBook;
import static com.javatemplate.fakes.BookFakes.buildBooks;
import static com.javatemplate.fakes.UserAuthenticationTokenFakes.buildAdmin;
import static com.javatemplate.fakes.UserAuthenticationTokenFakes.buildContributor;
import static java.util.UUID.randomUUID;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.apache.commons.lang3.RandomUtils.nextBytes;
import static org.assertj.core.util.Lists.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class BookServiceTest {

    @Mock
    private BookStore bookStore;

    @Mock
    private AuthsProvider authsProvider;

    @Mock
    private CloudinaryService cloudinaryService;

    @InjectMocks
    private BookService bookService;

    @Test
    void shouldFindAll_OK() {
        final var expected = buildBooks();

        when(bookStore.findAll()).thenReturn(expected);

        final var actual = bookService.findAll();

        assertEquals(expected.size(), actual.size());
        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(0).getAuthor(), actual.get(0).getAuthor());
        assertEquals(expected.get(0).getUserId(), actual.get(0).getUserId());
        assertEquals(expected.get(0).getUpdatedAt(), actual.get(0).getUpdatedAt());
        assertEquals(expected.get(0).getCreatedAt(), actual.get(0).getCreatedAt());
        assertEquals(expected.get(0).getImage(), actual.get(0).getImage());

        verify(bookStore).findAll();
    }

    @Test
    void shouldFindById_OK() {
        final var book = buildBook();

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));

        assertEquals(book, bookService.findById(book.getId()));
        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldFindById_ThrownNotFound() {
        final var uuid = randomUUID();

        when(bookStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findById(uuid));

        verify(bookStore).findById(uuid);
    }

    @Test
    void shouldCreateWithContributor_OK() {
        final var book = buildBook();

        when(bookStore.save(any(Book.class))).thenReturn(book);
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());

        final var actual = bookService.create(book);

        assertEquals(book, actual);
        verify(bookStore).save(any(Book.class));
    }

    @Test
    void shouldCreateWithoutName_ThrownBadRequest() {
        final var book = buildBook();
        book.setName(null);

        assertThrows(BadRequestException.class, () -> bookService.create(book));
    }

    @Test
    void shouldCreateWithoutDescription_ThrownBadRequest() {
        final var book = buildBook();
        book.setDescription(null);

        assertThrows(BadRequestException.class, () -> bookService.create(book));
    }

    @Test
    void shouldCreateWithoutAuthor_ThrownBadRequest() {
        final var book = buildBook();
        book.setAuthor(null);

        assertThrows(BadRequestException.class, () -> bookService.create(book));
    }

    @Test
    void shouldUpdateWithContributor_OK() {
        final var book = buildBook();
        final var bookUpdate = buildBook();

        when(bookStore.findById(book.getId()))
                .thenReturn(Optional.of(book));
        when(bookStore.save(book))
                .thenReturn(book);
        when(authsProvider.getCurrentUserId())
                .thenReturn(buildContributor().getUserId());
        when(authsProvider.getCurrentUserRole())
                .thenReturn(buildContributor().getRole());

        book.setUserId(authsProvider.getCurrentUserId());
        bookUpdate.setUserId(authsProvider.getCurrentUserId());
        bookUpdate.setId(book.getId());

        final var actual = bookService.update(book.getId(), bookUpdate);

        assertEquals(bookUpdate.getId(), actual.getId());
        assertEquals(bookUpdate.getName(), actual.getName());
        assertEquals(bookUpdate.getAuthor(), actual.getAuthor());
        assertEquals(bookUpdate.getImage(), actual.getImage());
        assertEquals(bookUpdate.getDescription(), actual.getDescription());
        assertEquals(bookUpdate.getUserId(), actual.getUserId());

        verify(bookStore).findById(book.getId());
        verify(bookStore).save(book);
    }

    @Test
    void shouldUpdateWithContributor_ThroughAccessDeniedException() {
        final var book = buildBook();
        final var bookUpdate = buildBook();

        when(bookStore.findById(book.getId()))
                .thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserId())
                .thenReturn(buildContributor().getUserId());
        when(authsProvider.getCurrentUserRole())
                .thenReturn(buildContributor().getRole());

        assertThrows(AccessDeniedException.class, () -> bookService.update(book.getId(), bookUpdate));

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldUpdateWithAdmin_OK() {
        final var book = buildBook();
        final var bookUpdate = buildBook();

        when(bookStore.findById(book.getId()))
                .thenReturn(Optional.of(book));
        when(bookStore.save(book))
                .thenReturn(book);
        when(authsProvider.getCurrentUserRole())
                .thenReturn(buildAdmin().getRole());

        bookUpdate.setId(book.getId());

        final var actual = bookService.update(book.getId(), bookUpdate);

        assertEquals(bookUpdate.getId(), actual.getId());
        assertEquals(bookUpdate.getName(), actual.getName());
        assertEquals(bookUpdate.getAuthor(), actual.getAuthor());
        assertEquals(bookUpdate.getImage(), actual.getImage());
        assertEquals(bookUpdate.getDescription(), actual.getDescription());

        verify(bookStore).findById(book.getId());
        verify(bookStore).save(book);
    }

    @Test
    void shouldUpdate_WithNameEmpty() {
        final var book = buildBook();
        final var bookUpdate = buildBook()
                .withId(book.getId())
                .withName(null);

        when(bookStore.findById(book.getId()))
                .thenReturn(Optional.of(book));
        assertThrows(BadRequestException.class, () -> bookService.update(book.getId(), bookUpdate));
    }

    @Test
    void shouldUpdate_WithUserIdEmpty() {
        final var book = buildBook();
        final var bookUpdate = buildBook()
                .withId(book.getId())
                .withUserId(null);

        when(bookStore.findById(book.getId()))
                .thenReturn(Optional.of(book));
        assertThrows(BadRequestException.class, () -> bookService.update(book.getId(), bookUpdate));
    }

    @Test
    void shouldUpdate_WithAuthorEmpty() {
        final var book = buildBook();
        final var bookUpdate = buildBook();
        bookUpdate.setId(book.getId());
        bookUpdate.setAuthor(null);

        when(bookStore.findById(book.getId()))
                .thenReturn(Optional.of(book));
        assertThrows(BadRequestException.class, () -> bookService.update(book.getId(), bookUpdate));
    }

    @Test
    void shouldUploadImage_Contributor_OK() throws IOException {
        final var book = buildBook();
        final var bookUpdate = buildBook()
                .withId(book.getId());
        final var bytes = nextBytes(20);

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildContributor().getRole());
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());

        book.setUserId(authsProvider.getCurrentUserId());
        bookUpdate.setUserId(authsProvider.getCurrentUserId());

        bookUpdate.setImage(cloudinaryService.upload(bytes));
        bookUpdate.setCreatedAt(Instant.now());
        bookService.uploadImage(bookUpdate.getId(), bytes);

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldUploadImage_Admin_OK() throws IOException {
        final var book = buildBook();
        final var bookUpdate = buildBook()
                .withId(book.getId())
                .withUserId(book.getUserId());
        final var bytes = nextBytes(20);

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildAdmin().getRole());

        bookUpdate.setImage(cloudinaryService.upload(bytes));
        bookUpdate.setCreatedAt(Instant.now());
        bookUpdate.setUserId(authsProvider.getCurrentUserId());
        bookService.uploadImage(bookUpdate.getId(), bytes);

        verify(bookStore).findById(book.getId());
        verify(authsProvider).getCurrentUserRole();
    }

    @Test
    void shouldUploadImage_Contributor_ThrownAccessDeniedException() {
        final var book = buildBook();
        final var bytes = nextBytes(20);

        when(bookStore.findById(book.getId())).thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserRole()).thenReturn(buildContributor().getRole());
        when(authsProvider.getCurrentUserId()).thenReturn(buildContributor().getUserId());

        assertThrows(AccessDeniedException.class, () -> bookService.uploadImage(book.getId(), bytes));

        verify(bookStore).findById(book.getId());
        verify(authsProvider).getCurrentUserRole();
        verify(authsProvider).getCurrentUserId();
        verify(bookStore, never()).save(book);
    }

    @Test
    void shouldUploadImage_ThrownNotFound() {
        final var book = buildBook();
        final var bytes = nextBytes(20);

        when(bookStore.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.uploadImage(book.getId(), bytes));

        verify(bookStore).findById(book.getId());
        verify(bookStore, never()).save(book);
    }

    @Test
    void shouldUpdate_NotFound() {
        final var bookId = randomUUID();
        final var bookUpdate = buildBook();

        when(bookStore.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.update(bookId, bookUpdate));

        verify(bookStore).findById(bookId);
    }

    @Test
    void shouldDeleteId_OK() {
        final var book = buildBook();

        when(bookStore.findById(book.getId()))
                .thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserId())
                .thenReturn(buildContributor().getUserId());
        when(authsProvider.getCurrentUserRole())
                .thenReturn(buildContributor().getRole());

        book.setUserId(authsProvider.getCurrentUserId());

        bookService.deleteById(book.getId());

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldDeleteId_ThroughAccessDeniedException() {
        final var book = buildBook();

        when(bookStore.findById(book.getId()))
                .thenReturn(Optional.of(book));
        when(authsProvider.getCurrentUserId())
                .thenReturn(buildContributor().getUserId());
        when(authsProvider.getCurrentUserRole())
                .thenReturn(buildContributor().getRole());

        assertThrows(AccessDeniedException.class, () -> bookService.deleteById(book.getId()));

        verify(bookStore).findById(book.getId());
    }

    @Test
    void shouldDeleteId_ThrownNotFound() {
        final var uuid = randomUUID();

        when(bookStore.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.deleteById(uuid));

        verify(bookStore).findById(uuid);
    }

    @Test
    void shouldFindBooksByNameAuthorDescription_OK() {
        final var book = buildBook();
        final var expected = buildBooks();

        when(bookStore.find(any(String.class))).thenReturn(expected);

        final var actual = bookService.find(book.getName());

        assertEquals(expected.size(), actual.size());

        verify(bookStore).find(book.getName());
    }

    @Test
    void shouldFindBooksByNameAuthorDescription_Empty() {
        final var input = randomAlphabetic(3, 10);

        when(bookStore.find(input)).thenReturn(emptyList());

        final var actual = bookService.find(input);

        assertTrue(actual.isEmpty());

        verify(bookStore).find(input);
    }

    @Test
    void shouldFindBookByIsbn13_OK() {
        final var book = buildBook();

        when(bookStore.findByIsbn13(book.getIsbn13())).thenReturn(Optional.of(book));

        assertEquals(book, bookService.findBookByIsbn13(book.getIsbn13()));
        verify(bookStore).findByIsbn13(book.getIsbn13());
    }

    @Test
    void shouldFindBookByIsbn13_ThrownNotFound() {
        final var isbn13 = randomNumeric(13);

        when(bookStore.findByIsbn13(isbn13)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> bookService.findBookByIsbn13(isbn13));
        verify(bookStore).findByIsbn13(isbn13);
    }
}
