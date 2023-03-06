package com.javatemplate.api.book;

import com.javatemplate.domain.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.javatemplate.api.book.BookDTOMapper.toBookDTO;
import static com.javatemplate.api.book.BookDTOMapper.toBookDTOs;

@RestController
@RequestMapping("api/v1/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Find all available books")
    @GetMapping
    public List<BookDTO> findAll() {
        return toBookDTOs(bookService.findAll());
    }

    @Operation(summary = "Find by id")
    @GetMapping("{bookId}")
    public BookDTO findById(final @PathVariable UUID bookId) {
        return toBookDTO(bookService.findById(bookId));
    }
}
