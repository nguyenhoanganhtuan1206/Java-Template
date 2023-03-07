package com.javatemplate.domain.book;

import com.javatemplate.persistent.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.javatemplate.domain.book.BookError.supplyBookNotFound;
import static com.javatemplate.error.CommonError.supplyValidationError;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;

    public List<Book> findAll() {
        return bookStore.findAll();
    }

    public Book findById(final UUID bookId) {
        return bookStore.findById(bookId)
                .orElseThrow(supplyBookNotFound(bookId));
    }

    public List<Book> findBooksByName(final String bookName) {
        return bookStore.findBooksByName(bookName);
    }

    public Book create(final Book book) {
        verifyData(book, "Create book failed. Please check your inputs");

        return bookStore.save(book);
    }

    public Book update(final UUID bookId, final Book bookUpdate) {
        verifyData(bookUpdate, "Update book failed. Please check your inputs");

        final Book book = findById(bookId);

        book.setName(bookUpdate.getName());
        book.setAuthor(bookUpdate.getAuthor());
        book.setImage(bookUpdate.getImage());
        book.setDescription(bookUpdate.getDescription());
        book.setUpdatedAt(Instant.now());
        book.setUserId(bookUpdate.getUserId());

        return bookStore.save(book);
    }

    public void deleteById(final UUID uuid) {
        final Book book = findById(uuid);

        bookStore.deleteById(book.getId());
    }

    private void verifyData(final Book book, final String message) {
        if (book.getAuthor() == null || book.getName() == null) {
            throw supplyValidationError(message).get();
        }
    }
}
