package com.javatemplate.persistent.book;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    List<BookEntity> findByNameContainingIgnoreCase(final String bookName);
}
