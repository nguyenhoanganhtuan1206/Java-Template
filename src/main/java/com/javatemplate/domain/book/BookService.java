package com.javatemplate.domain.book;

import com.javatemplate.domain.auth.AuthsProvider;
import com.javatemplate.persistent.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.javatemplate.domain.book.BookError.supplyBookNotFound;
import static com.javatemplate.error.CommonError.supplyAccessDeniedError;
import static com.javatemplate.error.CommonError.supplyValidationError;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;

    private final AuthsProvider authsProvider;

    public List<Book> findAll() {
        return bookStore.findAll();
    }

    public Book findById(final UUID bookId) {
        return bookStore.findById(bookId).orElseThrow(supplyBookNotFound(bookId));
    }

    public List<Book> find(final String input) {
        return bookStore.find(input);
    }

    public Book create(final Book book) {
        validateBookCreateRequest(book);

        book.setUserId(authsProvider.getCurrentUserId());
        book.setCreatedAt(Instant.now());

        return bookStore.save(book);
    }

    public Book update(final UUID bookId, final Book bookUpdate) {
        validateBookUpdateRequest(bookUpdate);

        final Book book = findById(bookId);
        validateBookPermissions(book.getUserId(), "update");

        book.setName(bookUpdate.getName());
        book.setAuthor(bookUpdate.getAuthor());
        book.setImage(bookUpdate.getImage());
        book.setDescription(bookUpdate.getDescription());
        book.setUpdatedAt(Instant.now());

        return bookStore.save(book);
    }

    public void deleteById(final UUID uuid) {
        final Book book = findById(uuid);
        validateBookPermissions(book.getUserId(), "delete");

        bookStore.deleteById(book.getId());
    }


    private void validateBookPermissions(final UUID userId, final String action) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(userId)) {
            throw supplyAccessDeniedError().get();
        }
    }

    private void validateBookCreateRequest(final Book book) {
        if (isBlank(book.getAuthor())) {
            throw supplyValidationError("Author cannot be empty").get();
        }

        if (isBlank(book.getDescription())) {
            throw supplyValidationError("Description cannot be empty").get();
        }

        if (isBlank(book.getName())) {
            throw supplyValidationError("Book name cannot be empty").get();
        }
    }

    private void validateBookUpdateRequest(final Book book) {
        if (isBlank(book.getAuthor())) {
            throw supplyValidationError("Author cannot be empty").get();
        }

        if (book.getUserId() == null) {
            throw supplyValidationError("User cannot be empty").get();
        }

        if (isBlank(book.getName())) {
            throw supplyValidationError("Book name cannot be empty").get();
        }
    }
}
