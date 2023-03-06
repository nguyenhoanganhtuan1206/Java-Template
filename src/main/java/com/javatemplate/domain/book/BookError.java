package com.javatemplate.domain.book;

import com.javatemplate.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class BookError {

    public static Supplier<NotFoundException> supplyUBookNotFound(final UUID bookId) {
        return () -> new NotFoundException("Book with id %s not found", bookId);
    }
}
