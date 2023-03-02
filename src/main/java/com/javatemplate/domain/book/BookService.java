package com.javatemplate.domain.book;

import com.javatemplate.error.DomainException;
import com.javatemplate.persistent.book.BookStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookStore bookStore;

    public List<Book> findAll() {
        final List<Book> books = bookStore.findAll();

        if (books.size() == 0) {
            throw new DomainException(HttpStatus.NO_CONTENT, "Book is empty");
        }

        return books;
    }
}
