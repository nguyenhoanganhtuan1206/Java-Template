package com.javatemplate.api.book;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookRequestDTO {

    private String name;

    private String author;

    private String description;

    private String image;
}
