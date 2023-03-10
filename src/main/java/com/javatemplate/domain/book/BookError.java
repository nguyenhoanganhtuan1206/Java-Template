package com.javatemplate.domain.book;

import com.javatemplate.error.BadRequestException;
import com.javatemplate.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.UUID;
import java.util.function.Supplier;

@UtilityClass
public class BookError {

    public static Supplier<NotFoundException> supplyBookNotFound(final UUID bookId) {
        return () -> new NotFoundException("Book with id %s not found", bookId);
    }

    public static Supplier<BadRequestException> supplyBookPermission() {
        return () -> new BadRequestException("You don't have permission to update this book");
    }
}
