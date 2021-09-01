package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.dto.AuthorDTO;
import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
        List<AuthorDTO> response = authorService.getAll();
        Assertions.assertEquals(2, response.size());
    }

    @Test
    void testGetById() {
        AuthorDTO response = authorService.getById(1L);
        Assertions.assertEquals("Bob", response.getFirstName());
        Assertions.assertEquals("Maximilliam Gustav III", response.getLastName());
        Assertions.assertEquals(new BigDecimal("0.0").toString(), response.getBalance());
    }

    @Test
    void testGetByIdNotFound() {
        AuthorDTO response = authorService.getById(IDNOTFOUND);
        Assertions.assertNull(response);
    }

    @Test
    void testGetAuthorsBlogPosts() {
        Set<BlogPost> response = authorService.getAuthorsBlogPosts(1L);
        Assertions.assertEquals(2, response.size());
    }

    @Test
    void testGetAuthorsBlogPostsNotFound() {
        Set<BlogPost> response = authorService.getAuthorsBlogPosts(IDNOTFOUND);
        Assertions.assertNull(response);
    }

    @Test
    void testCreate() {
        authorService.create(this.author);
        verify(authorRepository, times(1)).save(this.author);
    }

    @Test
    void testPutById() {
        AuthorDTO response = authorService.putById(2L, this.newAuthor);
        Assertions.assertEquals(2L, response.getId());
        verify(authorRepository, times(1)).save(this.newAuthor);
    }

    @Test
    void testPutByIdNotFound() {
        AuthorDTO response = authorService.putById(IDNOTFOUND, new Author());
        Assertions.assertNull(response);
    }

    @Test
    void testDonate() {
        boolean response = authorService.donate(1L, BigDecimal.valueOf(100L));
        Assertions.assertTrue(response);
        Assertions.assertEquals(BigDecimal.valueOf(100.0), this.author.getBalance());
        verify(authorRepository, times(1)).save(this.author);
    }

    @Test
    void testDonateNotFound() {
        boolean response = authorService.donate(IDNOTFOUND, BigDecimal.valueOf(10));
        Assertions.assertFalse(response);
    }

    @Test
    void testDeleteById() {
        boolean response = authorService.deleteById(1L);
        Assertions.assertTrue(response);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByIdNotFound() {
        boolean response = authorService.deleteById(IDNOTFOUND);
        Assertions.assertFalse(response);
    }
}
