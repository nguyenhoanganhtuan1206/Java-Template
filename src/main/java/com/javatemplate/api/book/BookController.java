package com.javatemplate.api.book;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    @Operation(summary = "Find all available books")
    @GetMapping
    public List<BookDTO> findAll() {
        return emptyList();
    }
}
