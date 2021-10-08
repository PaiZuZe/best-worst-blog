package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import com.paizuze.bestWorstBlog.repository.BlogPostRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class BlogPostServiceTest {
    BlogPostRepository blogPostRepository;
    AuthorRepository authorRepository;

    @InjectMocks
    BlogPostService blogPostService;

    private final long ID_NOT_FOUND = 100L;
    private final Author author = new Author("Jeff", "The Bob supreme");
    private final Author author2 = new Author("Bob", "Carlos Rodriguez");
    private final BlogPost blogPost = new BlogPost("Test 1", "Lorem Ipsum");
    private final BlogPost oldBlogPost = new BlogPost("Bad Title", "Lobem Ipbum");
    private final BlogPost changedBlogPost = new BlogPost("Better Title", "Lorem Ipsum");
    private final BlogPost newBlogPost = new BlogPost("A new post", "Some text for the new post");


    @BeforeAll
    void setUp() {
        this.author.setId(1L);
        this.blogPost.setId(2L);
        this.oldBlogPost.setId(3L);
        this.changedBlogPost.setId(4L);
        this.author2.setId(5L);
        this.newBlogPost.setId(6L);
        this.blogPost.setAuthor(this.author);
        this.oldBlogPost.setAuthor(this.author2);
        this.author.setBlogPosts(new HashSet<>(List.of(this.blogPost)));
        this.author2.setBlogPosts(new HashSet<>(List.of(this.oldBlogPost)));
        this.blogPostRepository = mock(BlogPostRepository.class);
        this.authorRepository = mock(AuthorRepository.class);

        Mockito.when(blogPostRepository.findById(ID_NOT_FOUND)).thenReturn(Optional.empty());
        Mockito.when(blogPostRepository.findById(2L)).thenReturn(Optional.of(this.blogPost));
        Mockito.when(blogPostRepository.findById(3L)).thenReturn(Optional.of(this.oldBlogPost));
        Mockito.when(blogPostRepository.findAll()).thenReturn(List.of(this.blogPost, this.oldBlogPost));
        Mockito.when(blogPostRepository.save(changedBlogPost)).thenReturn(changedBlogPost);
        Mockito.when(blogPostRepository.save(newBlogPost)).thenReturn(newBlogPost);
        Mockito.when(authorRepository.findById(ID_NOT_FOUND)).thenReturn(Optional.empty());
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(this.author));
    }

    @BeforeEach
    void cleanUp() {
        clearInvocations(this.blogPostRepository);
    }

    @Test
    void testGetAll() {
        List<BlogPost> response = blogPostService.getAll();
        Assertions.assertEquals(2, response.size());
    }

    @Test
    void testGetById() {
        BlogPost response = blogPostService.getById(2L);
        Assertions.assertEquals("Test 1", response.getTitle());
        Assertions.assertEquals("Lorem Ipsum", response.getTextBody());
    }

    @Test
    void testByIdNotFound() {
        BlogPost response = blogPostService.getById(ID_NOT_FOUND);
        Assertions.assertNull(response);
    }

    @Test
    void testCreate() {
        BlogPost resp = this.blogPostService.create(1L, newBlogPost);
        Assertions.assertEquals(1L, resp.getAuthor().getId());
        verify(blogPostRepository, times(1)).save(newBlogPost);
    }

    @Test
    void testCreateIdNotFound() {
        BlogPost resp = this.blogPostService.create(this.ID_NOT_FOUND, new BlogPost());
        Assertions.assertNull(resp);
    }

    @Test
    void testPutById() {
        BlogPost response = blogPostService.putById(3L, this.changedBlogPost);
        Assertions.assertEquals(3L, response.getId());
        Assertions.assertEquals(this.author2.getId(), response.getAuthor().getId());
        verify(blogPostRepository, times(1)).save(this.changedBlogPost);
    }

    @Test
    void testPutByIdNotFound() {
        BlogPost response = blogPostService.putById(ID_NOT_FOUND, new BlogPost());
        Assertions.assertNull(response);
    }

    @Test
    void testDeleteById(){
        boolean response = blogPostService.deleteById(2L);
        Assertions.assertTrue(response);
        verify(blogPostRepository, times(1)).deleteById(2L);
    }

    @Test
    void testDeleteByIdNotFound() {
        boolean response = blogPostService.deleteById(ID_NOT_FOUND);
        Assertions.assertFalse(response);
    }
}
