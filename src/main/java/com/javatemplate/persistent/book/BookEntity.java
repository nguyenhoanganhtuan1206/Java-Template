package com.javatemplate.persistent.book;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String author;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private String image;

    private UUID userId;
}
