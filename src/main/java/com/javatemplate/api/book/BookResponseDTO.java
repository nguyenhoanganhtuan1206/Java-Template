package com.javatemplate.api.book;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
public class BookResponseDTO {

    private UUID id;

    private String name;

    private String author;

    private String description;

    private Instant createdAt;

    private Instant updatedAt;

    private String image;

    private UUID userId;
}
