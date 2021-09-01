package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.dto.BlogPostDTO;
import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import com.paizuze.bestWorstBlog.repository.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final AuthorRepository authorRepository;

    public BlogPostService(AuthorRepository authorRepository, BlogPostRepository blogPostRepository) {
        this.authorRepository = authorRepository;
        this.blogPostRepository = blogPostRepository;
    }

    public ResponseEntity<Page<BlogPostDTO>> getPage(Pageable pageable) {
        return new ResponseEntity<>(blogPostRepository.findAll(pageable).map(BlogPost::toBlogPostDTO), HttpStatus.OK);
    }

    public ResponseEntity<List<BlogPostDTO>> getAll() {
        return new ResponseEntity<>(blogPostRepository.findAll().stream().map(BlogPost::toBlogPostDTO).collect(Collectors.toList()), HttpStatus.OK);
    }

    public ResponseEntity<BlogPostDTO> getById(long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.isPresent() ? new ResponseEntity<>(blogPost.get().toBlogPostDTO(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<BlogPostDTO> create(long author_id, BlogPost newBlogPost) {
        Optional<Author> author = authorRepository.findById(author_id);
        if (author.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newBlogPost.setAuthor(author.get());
        newBlogPost = blogPostRepository.save(newBlogPost);
        return new ResponseEntity<>(newBlogPost.toBlogPostDTO(), HttpStatus.CREATED);
    }

    public ResponseEntity<BlogPostDTO> putById(long id, BlogPost changed_blogPost) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            changed_blogPost.setId(blogPost.get().getId());
            changed_blogPost.setAuthor(blogPost.get().getAuthor());
            changed_blogPost = blogPostRepository.save(changed_blogPost);
            return new ResponseEntity<>(changed_blogPost.toBlogPostDTO(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<?> deleteById(long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            blogPostRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
