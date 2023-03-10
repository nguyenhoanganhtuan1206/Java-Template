package com.javatemplate.persistent.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    Optional<BookEntity> findByUserId(final UUID userId);

    @Query("SELECT b FROM BookEntity b WHERE CONCAT(b.name, b.author) like %:input%")
    List<BookEntity> findByNameOrAuthor(final String input);
}
