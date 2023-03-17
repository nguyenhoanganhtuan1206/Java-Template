package com.javatemplate.persistent.book;

import com.javatemplate.domain.book.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.javatemplate.persistent.book.BookEntityMapper.*;
import static org.apache.commons.collections4.IterableUtils.toList;

@Repository
@RequiredArgsConstructor
public class BookStore {

    private final BookRepository bookRepository;

    public List<Book> findAll() {
        return toBooks(toList(bookRepository.findAll()));
    }

    public Optional<Book> findById(final UUID bookId) {
        return bookRepository.findById(bookId)
                .map(BookEntityMapper::toBook);
    }

    public List<Book> find(final String searchTerm) {
        return toBooks(bookRepository.find(searchTerm));
    }

    public Book save(final Book book) {
        return toBook(bookRepository.save(toBookEntity(book)));
    }

    public void deleteById(final UUID uuid) {
        bookRepository.deleteById(uuid);
    }
}
