package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.dto.BlogPostDTO;
import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.BlogPostRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BlogPostServiceTest {
    BlogPostRepository blogPostRepository;

    @InjectMocks
    BlogPostService blogPostService;

    private final long IDNOTFOUND = 100L;
    private final Author author = new Author("Jeff", "The Bob supreme");
    private final Author author2 = new Author("Bob", "Carlos Rodriguez");
    private final BlogPost blogPost = new BlogPost("Test 1", "Lorem Ipsum");
    private final BlogPost oldBlogPost = new BlogPost("Bad Title", "Lobem Ipbum");
    private final BlogPost newBlogPost = new BlogPost("Better Title", "Lorem Ipsum");


    @BeforeAll
    void setUp() {
        this.author.setId(1L);
        this.blogPost.setId(2L);
        this.oldBlogPost.setId(3L);
        this.newBlogPost.setId(4L);
        this.author2.setId(5L);
        this.blogPost.setAuthor(this.author);
        this.oldBlogPost.setAuthor(this.author2);
        this.author.setBlogPosts(new HashSet<>(List.of(this.blogPost)));
        this.author2.setBlogPosts(new HashSet<>(List.of(this.oldBlogPost)));
        this.blogPostRepository = mock(BlogPostRepository.class);

        Mockito.when(blogPostRepository.findById(IDNOTFOUND)).thenReturn(Optional.empty());
        Mockito.when(blogPostRepository.findById(2L)).thenReturn(Optional.of(this.blogPost));
        Mockito.when(blogPostRepository.findById(3L)).thenReturn(Optional.of(this.oldBlogPost));
        Mockito.when(blogPostRepository.findAll()).thenReturn(List.of(this.blogPost, this.oldBlogPost));
        Mockito.when(blogPostRepository.save(newBlogPost)).thenReturn(newBlogPost);
    }

    @BeforeEach
    void cleanUp() {
        clearInvocations(this.blogPostRepository);
    }

    @Test
    void testGetAll() {
        ResponseEntity<List<BlogPostDTO>> response = blogPostService.getAll();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(2, response.getBody().size());
    }

    @Test
    void testGetById() {
        ResponseEntity<BlogPostDTO> response = blogPostService.getById(2L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Test 1", response.getBody().getTitle());
        Assertions.assertEquals("Lorem Ipsum", response.getBody().getTextBody());
    }

    @Test
    void testByIdNotFound() {
        ResponseEntity<BlogPostDTO> response = blogPostService.getById(IDNOTFOUND);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testPutById() {
        ResponseEntity<BlogPostDTO> response = blogPostService.putById(3L, newBlogPost);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(3L, response.getBody().getId());
        Assertions.assertEquals(this.author2.getId(), response.getBody().getAuthorId());
        verify(blogPostRepository, times(1)).save(newBlogPost);
    }

    @Test
    void testPutByIdNotFound() {
        ResponseEntity<BlogPostDTO> response = blogPostService.getById(IDNOTFOUND);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteById(){
        ResponseEntity<?> response = blogPostService.deleteById(2L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(blogPostRepository, times(1)).deleteById(2L);
    }

    @Test
    void testDeleteByIdNotFound() {
        ResponseEntity<?> response = blogPostService.deleteById(IDNOTFOUND);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
