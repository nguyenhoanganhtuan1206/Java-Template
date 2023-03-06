package com.javatemplate.domain.book;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
public class Book {

    private UUID id;

    private String title;

    private String author;

    private String description;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private String image;

    private UUID userId;
}
