package com.javatemplate.domain.book;

import com.javatemplate.error.BadRequestException;
import com.javatemplate.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class BookError {

    public static Supplier<NotFoundException> supplyBookNotFound(final String fieldName, final String fieldValue) {
        return () -> new NotFoundException("Book with %s %s not found", fieldName, fieldValue);
    }

    public static Supplier<BadRequestException> supplyBookAlreadyExisted(final String isbn13) {
        return () -> new BadRequestException("Book with isbn13 %s already exist", isbn13);
    }
}
