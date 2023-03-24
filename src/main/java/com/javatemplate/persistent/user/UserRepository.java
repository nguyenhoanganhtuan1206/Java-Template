package com.javatemplate.persistent.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    @Query("SELECT u FROM UserEntity u WHERE CONCAT(u.username, u.firstName, u.lastName) ILIKE %:name% and u.enabled = true")
    List<UserEntity> findByName(final String name);

    Optional<UserEntity> findByUsernameAndEnabledTrue(final String username);
}
