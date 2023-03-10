package com.javatemplate.persistent.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    @Query("SELECT b FROM BookEntity b WHERE CONCAT(b.name, b.author, b.description) like %:searchTerm%")
    List<BookEntity> findByNameAuthorDesc(final String searchTerm);
}
