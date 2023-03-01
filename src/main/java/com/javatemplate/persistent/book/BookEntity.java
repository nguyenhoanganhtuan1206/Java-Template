package com.javatemplate.persistent.book;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Table(name = "books")
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {

    private UUID id;
    private String name;
}
