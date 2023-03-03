package com.javatemplate.domain.book;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class Book {

    private UUID id;

    private String name;
}
