package com.javatemplate.persistent.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    @Query(value = "SELECT * " +
            "FROM books " +
            "WHERE title ILIKE CONCAT ('%', :keyword, '%') " +
            "   OR author ILIKE CONCAT ('%', :keyword, '%') " +
            "   OR subtitle ILIKE CONCAT ('%', :keyword, '%') " +
            "   OR publisher ILIKE CONCAT ('%', :keyword, '%') " +
            "   OR isbn13 ILIKE CONCAT ('%', :keyword, '%') " +
            "   OR description ILIKE CONCAT ('%', :keyword, '%')", nativeQuery = true)
    List<BookEntity> find(final String keyword);

    Optional<BookEntity> findByIsbn13(final String isbn13);
}
