package com.javatemplate.domain.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Book {

    private UUID id;

    private String name;

    private String author;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private String image;

    private String subtitle;

    private String publisher;

    private String isbn13;

    private String price;

    private Integer year;

    private Double rating;

    private UUID userId;
}
