package com.javatemplate.persistent.book;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "books")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookEntity {

    public static final String ID_FIELD = "id";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private String author;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String image;

    private UUID userId;
}
