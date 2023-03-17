package com.javatemplate.api.book;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class BookUpdateRequestDTO {

    private String name;

    private String author;

    private String description;

    private String image;

    private UUID userId;
}
