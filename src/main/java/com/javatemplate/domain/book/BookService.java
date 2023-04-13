package com.javatemplate.domain.book;

import com.javatemplate.domain.auth.AuthsProvider;
import com.javatemplate.persistent.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final CloudinaryService cloudinaryService;

    public List<Book> findAll() {
        return bookStore.findAll();
    }

    public Book findById(final UUID id) {
        return bookStore.findById(id).orElseThrow(supplyBookNotFound("id", String.valueOf(id)));
    }

    public List<Book> find(final String keyword) {
        return bookStore.find(keyword);
    }

    public Book findBookByIsbn13(final String isbn13) {
        return bookStore.findByIsbn13(isbn13)
                .orElseThrow(supplyBookNotFound("isbn13", isbn13));
    }

    public Book create(final Book book) {
        validateBook(book);
        verifyIsbn13BookAvailable(book.getIsbn13());

        final double bookRating = book.getRating() == null ? 0.0 : book.getRating();

        final Book bookToCreate = book
                .withUserId(authsProvider.getCurrentUserId())
                .withIsbn13(book.getIsbn13())
                .withCreatedAt(Instant.now())
                .withRating(bookRating);

        return bookStore.save(bookToCreate);
    }

    public Book update(final UUID id, final Book book) {
        final Book bookToUpdate = findById(id);
        validateBook(book);
        verifyUpdateBookPermission(bookToUpdate);

        if (!bookToUpdate.getIsbn13().equals(book.getIsbn13())) {
            verifyIsbn13BookAvailable(book.getIsbn13());
            bookToUpdate.setIsbn13(book.getIsbn13());
        }

        bookToUpdate.setName(book.getName());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setDescription(book.getDescription());
        bookToUpdate.setImage(book.getImage());
        bookToUpdate.setSubtitle(book.getSubtitle());
        bookToUpdate.setPublisher(book.getPublisher());
        bookToUpdate.setPrice(book.getPrice());
        bookToUpdate.setYear(book.getYear());
        bookToUpdate.setRating(book.getRating());
        bookToUpdate.setUpdatedAt(Instant.now());

        return bookStore.save(bookToUpdate);
    }

    public void deleteById(final UUID id) {
        final Book book = findById(id);
        verifyDeleteBookPermission(book);

        bookStore.deleteById(id);
    }

    public void uploadImage(final UUID id, final byte[] image) throws IOException {
        final Book book = findById(id);
        verifyUpdateBookPermission(book);

        book.setImage(cloudinaryService.upload(image));
        book.setUpdatedAt(Instant.now());

        bookStore.save(book);
    }

    private void verifyUpdateBookPermission(final Book book) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(book.getUserId())) {
            throw supplyAccessDeniedError().get();
        }
    }

    private void verifyDeleteBookPermission(final Book book) {
        if (authsProvider.getCurrentUserRole().equals("ROLE_CONTRIBUTOR")
                && !authsProvider.getCurrentUserId().equals(book.getUserId())) {
            throw supplyAccessDeniedError().get();
        }
    }

    private void verifyIsbn13BookAvailable(final String isbn13) {
        bookStore.findByIsbn13(isbn13)
                .ifPresent(b -> {
                    throw supplyBookAlreadyExisted(isbn13).get();
                });
    }
}
