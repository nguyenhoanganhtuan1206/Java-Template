package com.javatemplate.persistent.book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, UUID> {

    @Query("select b from BookEntity b where b.author like %:searchTerm% or b.description like %:searchTerm% or b.name like %:searchTerm%")
    List<BookEntity> find(final String searchTerm);
}
