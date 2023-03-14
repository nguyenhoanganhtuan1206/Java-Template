package com.javatemplate.domain.book;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.With;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
@With
public class Book {

    private UUID id;

    private String name;

    private String author;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String image;

    private UUID userId;
}
