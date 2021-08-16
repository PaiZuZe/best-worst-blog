package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.BlogPostRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BlogPostServiceTest {
    @Mock
    BlogPostRepository blogPostRepository;

    @InjectMocks
    BlogPostService blogPostService;

    @Test
    void testGetById() {
        Author author = new Author("Jeff", "The Bob supreme");
        BlogPost blogPost = new BlogPost("Test 1", "Lorem Ipsum");
        blogPost.setAuthor(author);

        Optional<BlogPost> opt = Optional.of(blogPost);
        Mockito.when(blogPostRepository.findById(2L)).thenReturn(opt);

        ResponseEntity<BlogPost> response = blogPostService.getById(2L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Test 1", response.getBody().getTitle());
        Assertions.assertEquals("Lorem Ipsum", response.getBody().getTextBody());
    }

    @Test
    void testByIdNotFound() {
        Optional<BlogPost> opt = Optional.empty();
        Mockito.when(blogPostRepository.findById(2L)).thenReturn(opt);

        ResponseEntity<BlogPost> response = blogPostService.getById(2L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testPutByIdNotFound() {
        Optional<BlogPost> opt = Optional.empty();
        Mockito.when(blogPostRepository.findById(2L)).thenReturn(opt);

        ResponseEntity<BlogPost> response = blogPostService.getById(2L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteByIdNotFound() {
        Optional<BlogPost> opt = Optional.empty();
        Mockito.when(blogPostRepository.findById(2L)).thenReturn(opt);

        ResponseEntity<?> response = blogPostService.deleteById(2L);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
