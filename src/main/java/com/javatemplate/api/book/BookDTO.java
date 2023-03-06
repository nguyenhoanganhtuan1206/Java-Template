package com.javatemplate.api.book;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class BookDTO {

    private UUID id;

    private String title;

    private String author;

    private String description;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private String image;

    private UUID userId;
}
