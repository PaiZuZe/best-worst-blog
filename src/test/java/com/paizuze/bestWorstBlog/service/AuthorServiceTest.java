package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private final long IDNOTFOUND = 100L;
    private final Author author = new Author("Bob", "Maximilliam Gustav III");
    private final Author oldAuthor = new Author("J.", "Ronald Rel Tokien");
    private final Author newAuthor = new Author("John", "Ronald Reuel Tolkien");
    private final BlogPost blogPost = new BlogPost("Test", "This is a test");
    private final BlogPost blogPost1 = new BlogPost("Test 2", "This is also a test");

    @BeforeAll
    void setUp() {
        this.author.setId(1L);
        this.oldAuthor.setId(2L);
        this.newAuthor.setId(5L);
        this.blogPost.setId(3L);
        this.blogPost1.setId(4L);
        this.blogPost.setAuthor(this.author);
        this.blogPost1.setAuthor(this.author);
        this.author.setBlogPosts(new HashSet<>(List.of(this.blogPost, this.blogPost1)));
        this.authorRepository = mock(AuthorRepository.class);

        Mockito.when(authorRepository.findById(IDNOTFOUND)).thenReturn(Optional.empty());
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(this.author));
        Mockito.when(authorRepository.findById(2L)).thenReturn(Optional.of(this.oldAuthor));
        Mockito.when(authorRepository.findAll()).thenReturn(List.of(this.author, this.oldAuthor));
        Mockito.when(authorRepository.save(this.author)).thenReturn(this.author);
        Mockito.when(authorRepository.save(this.newAuthor)).thenReturn(this.newAuthor);
    }

    @BeforeEach
    void cleanUp() {
        clearInvocations(this.authorRepository);
    }

    @Test
    void testGetAll() {
        ResponseEntity<List<Author>> response = authorService.getAll();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetById() {
        ResponseEntity<Author> response = authorService.getById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Author authorResponse = response.getBody();
        Assertions.assertEquals("Bob", authorResponse.getFirstName());
        Assertions.assertEquals("Maximilliam Gustav III", authorResponse.getLastName());
        Assertions.assertEquals(new BigDecimal("0.0").longValue(), authorResponse.getBalance().longValue());
    }

    @Test
    void testGetByIdNotFound() {
        ResponseEntity<Author> response = authorService.getById(IDNOTFOUND);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAuthorsBlogPosts() {
        ResponseEntity<Set<BlogPost>> response = authorService.getAuthorsBlogPosts(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetAuthorsBlogPostsNotFound() {
        ResponseEntity<Set<BlogPost>> response = authorService.getAuthorsBlogPosts(IDNOTFOUND);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testCreate() {
        ResponseEntity<Author> response = authorService.create(this.author);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(authorRepository, times(1)).save(this.author);
    }

    @Test
    void testPutById() {
        ResponseEntity<Author> response = authorService.putById(2L, this.newAuthor);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2L, response.getBody().getId());
        verify(authorRepository, times(1)).save(this.newAuthor);
    }

    @Test
    void testPutByIdNotFound() {
        ResponseEntity<Author> response = authorService.putById(IDNOTFOUND, new Author());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDonate() {
        ResponseEntity<?> response = authorService.donate(1L, BigDecimal.valueOf(100L));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(BigDecimal.valueOf(100.0), this.author.getBalance());
        verify(authorRepository, times(1)).save(this.author);
    }

    @Test
    void testDonateNotFound() {
        ResponseEntity<?> response = authorService.donate(IDNOTFOUND, BigDecimal.valueOf(10));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteById() {
        ResponseEntity<?> response = authorService.deleteById(1L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByIdNotFound() {
        ResponseEntity<?> response = authorService.deleteById(IDNOTFOUND);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
