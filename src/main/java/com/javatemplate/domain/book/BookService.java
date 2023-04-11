package com.javatemplate.domain.book;

import com.javatemplate.domain.auth.AuthsProvider;
import com.javatemplate.persistent.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
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

    private final CloudinaryService cloudinaryService;

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

    public void uploadImage(final UUID id, final byte[] image) throws IOException {
        final Book book = findById(id);
        book.setImage(cloudinaryService.upload(image));
        book.setUpdatedAt(Instant.now());
        bookStore.save(book);
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
