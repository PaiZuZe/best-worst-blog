package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import com.paizuze.bestWorstBlog.repository.BlogPostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BlogPostServiceTest {
    @Autowired
    BlogPostService blogPostService;

    @Autowired
    BlogPostRepository blogPostRepository;

    @Autowired
    AuthorRepository authorRepository;

    long NOTFOUNDID = 100L;

    @BeforeAll
    void setUp() {
        Author author = new Author("Jeff", "The Bob supreme");
        BlogPost blogPost = new BlogPost("Test 1", "Lorem Ipsum");
        blogPost.setAuthor(author);
        BlogPost blogPost1 = new BlogPost("Test 2", "Lorem ipsum dolor sit amet, consectetur adipiscing");
        blogPost1.setAuthor(author);

        authorRepository.save(author);
        blogPostRepository.save(blogPost);
        blogPostRepository.save(blogPost1);
    }

    @AfterAll
    void cleanUp() {
        blogPostRepository.deleteById(3L);
        blogPostRepository.deleteById(2L);
        authorRepository.deleteById(1L);
    }

    @Test
    void testGetById() {
        ResponseEntity<BlogPost> response = blogPostService.getById(2L);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Test 1", response.getBody().getTitle());
        Assertions.assertEquals("Lorem Ipsum", response.getBody().getTextBody());
    }

    @Test
    void testByIdNotFound() {
        ResponseEntity<BlogPost> response = blogPostService.getById(NOTFOUNDID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testPutById() {
        BlogPost changedBlogPost = new BlogPost("A better title", "Lorem ipsum is just not that great");
        ResponseEntity<BlogPost> response = blogPostService.putById(3L, changedBlogPost);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        BlogPost blogPost = blogPostService.getById(3L).getBody();
        Assertions.assertEquals("A better title", blogPost.getTitle());
        Assertions.assertEquals("Lorem ipsum is just not that great", blogPost.getTextBody());
    }

    @Test
    void testPutByIdNotFound() {
        ResponseEntity<BlogPost> response = blogPostService.getById(NOTFOUNDID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testDeleteByIdNotFound() {
        ResponseEntity<?> response = blogPostService.deleteById(NOTFOUNDID);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
