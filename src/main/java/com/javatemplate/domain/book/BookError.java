package com.javatemplate.domain.book;

import com.javatemplate.error.BadRequestException;
import com.javatemplate.error.NotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class BookError {

    public static Supplier<NotFoundException> supplyBookNotFound(final String fieldName, final String fieldValue) {
        return () -> new NotFoundException("Book with %n %v not found", fieldName, fieldValue);
    }

    public static Supplier<BadRequestException> supplyBookAlreadyExisted(final String fieldName, final String fieldValue) {
        return () -> new BadRequestException("Book with %n %v is already existed", fieldName, fieldValue);
    }
}
