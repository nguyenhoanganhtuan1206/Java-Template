package com.javatemplate.domain.book;

import com.javatemplate.domain.auth.AuthsProvider;
import com.javatemplate.persistent.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.javatemplate.domain.book.BookError.supplyBookAlreadyExisted;
import static com.javatemplate.domain.book.BookError.supplyBookNotFound;
import static com.javatemplate.domain.book.BookValidation.validateBook;
import static com.javatemplate.error.CommonError.supplyAccessDeniedError;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;

    private final AuthsProvider authsProvider;

    public List<Book> findAll() {
        return bookStore.findAll();
    }

    public Book findById(final UUID bookId) {
        return bookStore.findById(bookId).orElseThrow(supplyBookNotFound("id", String.valueOf(bookId)));
    }

    public Book findByIsbn13(final String isbn13) {
        return bookStore.findByIsbn13(isbn13).orElseThrow(supplyBookNotFound("Isbn13", isbn13));
    }

    public List<Book> find(final String input) {
        return bookStore.find(input);
    }

    public Book create(final Book book) {
        validateBook(book);
        verifyIfIsbn13Available(book.getIsbn13());

        final double bookRating = book.getRating() == null ? 0.0 : book.getRating();

        final Book bookToCreate = book
                .withUserId(authsProvider.getCurrentUserId())
                .withIsbn13(book.getIsbn13())
                .withCreatedAt(Instant.now())
                .withRating(bookRating);

        return bookStore.save(bookToCreate);
    }

    public Book update(final UUID bookId, final Book bookUpdate) {
        validateBook(bookUpdate);

        final Book book = findById(bookId);
        validateDeletePermission(book);

        book.setName(bookUpdate.getName());
        book.setAuthor(bookUpdate.getAuthor());
        book.setImage(bookUpdate.getImage());
        book.setDescription(bookUpdate.getDescription());
        book.setUpdatedAt(Instant.now());

        return bookStore.save(book);
    }

    public void deleteById(final UUID uuid) {
        final Book book = findById(uuid);
        validateUpdatePermission(book);

        bookStore.deleteById(book.getId());
    }

    private void verifyIfIsbn13Available(final String isbn13) {
        bookStore.findByIsbn13(isbn13)
                .ifPresent(b -> {
                    throw supplyBookAlreadyExisted("isbn13", isbn13).get();
                });
    }

    private void validateDeletePermission(final Book book) {
        if (StringUtils.equals(authsProvider.getCurrentUserRole(), "ROLE_ADMIN")) {
            return;
        }

        if (!StringUtils.equals(authsProvider.getCurrentUserId().toString(), book.getUserId().toString())) {
            throw supplyAccessDeniedError().get();
        }
    }

    private void validateUpdatePermission(final Book book) {
        if (StringUtils.equals(authsProvider.getCurrentUserRole(), "ROLE_ADMIN")) {
            return;
        }

        if (!StringUtils.equals(authsProvider.getCurrentUserId().toString(), book.getUserId().toString())) {
            throw supplyAccessDeniedError().get();
        }
    }
}
