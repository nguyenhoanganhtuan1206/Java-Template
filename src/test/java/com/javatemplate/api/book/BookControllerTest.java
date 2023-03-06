package com.javatemplate.api.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.javatemplate.domain.book.Book;
import com.javatemplate.domain.book.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javatemplate.fakes.BookFakes.buildBook;
import static com.javatemplate.fakes.BookFakes.buildBooks;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerTest {

    private static final String BASE_URL = "/api/v1/books";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @Test
    void shouldFindAll_Ok() throws Exception {
        final var books = buildBooks();

        when(bookService.findAll()).thenReturn(books);

        this.mvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(books.size()))
                .andExpect(jsonPath("$[0].id").value(books.get(0).getId().toString()))
                .andExpect(jsonPath("$[0].name").value(books.get(0).getName()))
                .andExpect(jsonPath("$[0].author").value(books.get(0).getAuthor()))
                .andExpect(jsonPath("$[0].description").value(books.get(0).getDescription()))
                .andExpect(jsonPath("$[0].createdAt").value(books.get(0).getCreatedAt().toString()))
                .andExpect(jsonPath("$[0].updatedAt").value(books.get(0).getUpdatedAt().toString()))
                .andExpect(jsonPath("$[0].userId").value(books.get(0).getUserId().toString()))
                .andExpect(jsonPath("$[0].image").value(books.get(0).getImage()));

        verify(bookService).findAll();
    }

    @Test
    void shouldFindById_Ok() throws Exception {
        final var book = buildBook();

        when(bookService.findById(book.getId())).thenReturn(book);

        this.mvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/" + book.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()));

        verify(bookService).findById(book.getId());
    }

    @Test
    void shouldCreate_Ok() throws Exception {
        final var book = buildBook();

        when(bookService.create(any(Book.class))).thenReturn(book);

        this.mvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(book)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(book.getId().toString()))
                .andExpect(jsonPath("$.name").value(book.getName()))
                .andExpect(jsonPath("$.author").value(book.getAuthor()))
                .andExpect(jsonPath("$.description").value(book.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(book.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(book.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.userId").value(book.getUserId().toString()))
                .andExpect(jsonPath("$.image").value(book.getImage()));
    }

    @Test
    void shouldUpdate_Ok() throws Exception {
        final var bookToUpdate = buildBook();
        final var bookUpdate = buildBook();
        bookUpdate.setId(bookToUpdate.getId());
        
        when(bookService.update(eq(bookToUpdate.getId()), any(Book.class)))
                .thenReturn(bookUpdate);

        this.mvc.perform(MockMvcRequestBuilders.patch(BASE_URL + "/" + bookToUpdate.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(bookUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(bookUpdate.getId().toString()))
                .andExpect(jsonPath("$.name").value(bookUpdate.getName()))
                .andExpect(jsonPath("$.author").value(bookUpdate.getAuthor()))
                .andExpect(jsonPath("$.description").value(bookUpdate.getDescription()))
                .andExpect(jsonPath("$.createdAt").value(bookUpdate.getCreatedAt().toString()))
                .andExpect(jsonPath("$.updatedAt").value(bookUpdate.getUpdatedAt().toString()))
                .andExpect(jsonPath("$.userId").value(bookUpdate.getUserId().toString()))
                .andExpect(jsonPath("$.image").value(bookUpdate.getImage()));
    }

    @Test
    void shouldDeleteById_Ok() throws Exception {
        final var book = buildBook();

        this.mvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + book.getId()))
                .andExpect(status().isOk());

        verify(bookService).deleteById(book.getId());
    }
}
