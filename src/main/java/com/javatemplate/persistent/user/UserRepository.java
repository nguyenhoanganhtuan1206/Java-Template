package com.javatemplate.persistent.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    @Query("select u from UserEntity u where u.username = ?1")
    Optional<UserEntity> findByUsername(final String username);
}
