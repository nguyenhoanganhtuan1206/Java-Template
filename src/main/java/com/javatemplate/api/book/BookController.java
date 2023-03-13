package com.javatemplate.api.book;

import com.javatemplate.domain.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.javatemplate.api.book.BookDTOMapper.*;

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

    @Operation(summary = "Find books by name, author and description")
    @GetMapping("/search")
    public List<BookDTO> findBooksByName(final @RequestParam String searchTerm) {
        return toBookDTOs(bookService.findByNameAuthorDescription(searchTerm));
    }

    @Operation(summary = "Create book")
    @PostMapping
    public BookDTO create(final @RequestBody BookDTO bookDTO) {
        return toBookDTO(bookService.create(toBook(bookDTO)));
    }

    @Operation(summary = "Update book")
    @PutMapping("{bookId}")
    public BookDTO update(final @PathVariable UUID bookId, final @RequestBody BookDTO bookDTO) {
        return toBookDTO(bookService.update(bookId, toBook(bookDTO)));
    }

    @Operation(summary = "Delete book by id")
    @DeleteMapping("{bookId}")
    public void deleteById(final @PathVariable UUID bookId) {
        bookService.deleteById(bookId);
    }
}
