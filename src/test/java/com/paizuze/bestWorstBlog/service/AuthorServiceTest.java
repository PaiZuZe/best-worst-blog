package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void testGetByIdFound() {
        Author author = new Author("Bob", "Maximilliam Gustav III");
        Optional<Author> opt = Optional.of(author);
        Mockito.when(authorRepository.findById(1L)).thenReturn(opt);

        ResponseEntity<Author> response = authorService.getById(1L);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Author authorResponse = response.getBody();
        Assertions.assertEquals("Bob", authorResponse.getFirstName());
        Assertions.assertEquals("Maximilliam Gustav III", authorResponse.getLastName());
        Assertions.assertEquals(new BigDecimal("0.0").longValue(), authorResponse.getBalance().longValue());
    }
    @Test
    void testGetByIdNotFound() {
        Optional<Author> opt = Optional.empty();
        Mockito.when(authorRepository.findById(1L)).thenReturn(opt);

        ResponseEntity<Author> response = authorService.getById(1L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    void testPutByIdNotFound() {
        Optional<Author> opt = Optional.empty();
        Mockito.when(authorRepository.findById(1L)).thenReturn(opt);

        ResponseEntity<Author> response = authorService.putById(1L, new Author());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDonateNotFound() {
        Optional<Author> opt = Optional.empty();
        Mockito.when(authorRepository.findById(1L)).thenReturn(opt);

        ResponseEntity<?> response = authorService.donate(1L, BigDecimal.valueOf(10));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteByIdNotFound() {
        Optional<Author> opt = Optional.empty();
        Mockito.when(authorRepository.findById(1L)).thenReturn(opt);

        ResponseEntity<?> response = authorService.deleteById(1L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
