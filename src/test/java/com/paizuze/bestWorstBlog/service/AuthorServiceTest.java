package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthorServiceTest {
    @Autowired
    AuthorService authorService;

    @Autowired
    AuthorRepository authorRepository;

    long NOTFOUNDID = 100L;

    @BeforeAll
    void setUp() {
        Author author = new Author("Bob", "Maximilliam Gustav III");
        authorRepository.save(author);

        Author author2 = new Author("J.", "Ronald Rel Tokien");
        authorRepository.save(author2);
    }

    @AfterAll
    void cleanUp() {
        authorRepository.deleteById(1L);
        authorRepository.deleteById(2L);
    }

    @Test
    void testGetByIdFound() {
        ResponseEntity<Author> response = authorService.getById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Author author = response.getBody();
        Assertions.assertEquals("Bob", author.getFirstName());
        Assertions.assertEquals("Maximilliam Gustav III", author.getLastName());
        Assertions.assertEquals(new BigDecimal("0.0").longValue(), author.getBalance().longValue());
    }

    @Test
    void testGetByIdNotFound() {
        ResponseEntity<Author> response = authorService.getById(NOTFOUNDID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testPutById() {
        Author changed_author = new Author("John", "Ronald Reuel Tolkien");
        changed_author.setBalance(new BigDecimal("100000.00"));

        ResponseEntity<Author> response = authorService.putById(2L, changed_author);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Author author = authorService.getById(2L).getBody();
        Assertions.assertEquals("John", author.getFirstName());
        Assertions.assertEquals("Ronald Reuel Tolkien", author.getLastName());
        Assertions.assertEquals(new BigDecimal("100000.00").doubleValue(), author.getBalance().doubleValue());

    }

    @Test
    void testPutByIdNotFound() {
        ResponseEntity<Author> response = authorService.putById(NOTFOUNDID, new Author());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testDonateNotFound() {
        ResponseEntity<?> response = authorService.donate(NOTFOUNDID, BigDecimal.valueOf(10));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDonate10(){
        BigDecimal old_balance = authorService.getById(1L).getBody().getBalance();
        ResponseEntity<?> response = authorService.donate(1L, BigDecimal.valueOf(10));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        BigDecimal new_balance = authorService.getById(1L).getBody().getBalance();
        Assertions.assertEquals(old_balance.add(BigDecimal.valueOf(10)).doubleValue(), new_balance.doubleValue());
    }

    @Test
    void testDonate5Dot55(){
        BigDecimal old_balance = authorService.getById(1L).getBody().getBalance();
        ResponseEntity<?> response = authorService.donate(1L,new BigDecimal("5.55"));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        BigDecimal new_balance = authorService.getById(1L).getBody().getBalance();
        Assertions.assertEquals(old_balance.add(new BigDecimal("5.55")).doubleValue(), new_balance.doubleValue());
    }

    @Test
    void testDeleteByIdNotFound() {
        ResponseEntity<?> response = authorService.deleteById(NOTFOUNDID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
