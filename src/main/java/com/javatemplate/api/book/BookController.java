package com.javatemplate.api.book;

import com.javatemplate.domain.book.BookService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public List<BookResponseDTO> findAll() {
        return toBookResponseDTOs(bookService.findAll());
    }

    @Operation(summary = "Find by id")
    @GetMapping("{bookId}")
    public BookResponseDTO findById(final @PathVariable UUID bookId) {
        return toBookResponseDTO(bookService.findById(bookId));
    }

    @Operation(summary = "Find books by name, author and description")
    @GetMapping("search")
    public List<BookResponseDTO> find(final @RequestParam String searchTerm) {
        return toBookResponseDTOs(bookService.find(searchTerm));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @Operation(summary = "Create book")
    @PostMapping
    public BookResponseDTO create(final @RequestBody BookRequestDTO bookDTO) {
        return toBookResponseDTO(bookService.create(toBookRequestDTO(bookDTO)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @PostMapping("{id}/image")
    public void upload(final @PathVariable UUID id, @RequestParam("file") final MultipartFile file) throws IOException {
        bookService.uploadImage(id, file.getBytes());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @Operation(summary = "Update book")
    @PutMapping("{bookId}")
    public BookResponseDTO update(final @PathVariable UUID bookId, final @RequestBody BookRequestDTO bookDTO) {
        return toBookResponseDTO(bookService.update(bookId, toBookRequestDTO(bookDTO)));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'CONTRIBUTOR')")
    @Operation(summary = "Delete book by id")
    @DeleteMapping("{bookId}")
    public void deleteById(final @PathVariable UUID bookId) {
        bookService.deleteById(bookId);
    }
}
