package com.javatemplate.persistent.book;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface BookRepository extends CrudRepository<BookEntity, UUID> {
}
