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

    public List<Book> findByNameAuthorDescription(final String input) {
        return bookStore.findByNameAuthorDescription(input);
    }

    public Book create(final Book book) {
        verifyBookInformation(book);

        book.setCreatedAt(Instant.now());
        return bookStore.save(book);
    }

    public Book update(final UUID bookId, final Book bookUpdate) {
        verifyBookInformation(bookUpdate);

        final Book book = findById(bookId);

        book.setName(bookUpdate.getName());
        book.setAuthor(bookUpdate.getAuthor());
        book.setImage(bookUpdate.getImage());
        book.setDescription(bookUpdate.getDescription());
        book.setUpdatedAt(Instant.now());

        return bookStore.save(book);
    }

    public void deleteById(final UUID uuid) {
        final Book book = findById(uuid);

        bookStore.deleteById(book.getId());
    }

    private void verifyBookInformation(final Book book) {
        if (book.getAuthor() == null) {
            throw supplyValidationError("Author cannot be empty").get();
        }

        if (book.getUserId() == null) {
            throw supplyValidationError("User cannot be empty").get();
        }

        if (book.getName() == null) {
            throw supplyValidationError("Book name cannot be empty").get();
        }
    }
}
