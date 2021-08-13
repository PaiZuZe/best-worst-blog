package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import com.paizuze.bestWorstBlog.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private AuthorRepository authorRepository;

    public ResponseEntity<Page<BlogPost>> getPage(Pageable pageable) {
        return new ResponseEntity<>(blogPostRepository.findAll(pageable), HttpStatus.OK);
    }

    public ResponseEntity<List<BlogPost>> getAll() {
        return new ResponseEntity<>(blogPostRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<BlogPost> getById(long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.isPresent() ? new ResponseEntity<>(blogPost.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<BlogPost> create(long author_id, BlogPost newBlogPost) {
        Optional<Author> author = authorRepository.findById(author_id);
        if (author.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        newBlogPost.setAuthor(author.get());
        newBlogPost = blogPostRepository.save(newBlogPost);
        return new ResponseEntity<>(newBlogPost, HttpStatus.CREATED);
    }

    public ResponseEntity<BlogPost> putById(long id, BlogPost changed_blogPost) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            changed_blogPost.setId(blogPost.get().getId());
            changed_blogPost.setAuthor(blogPost.get().getAuthor());
            blogPostRepository.save(changed_blogPost);
            return new ResponseEntity<>(changed_blogPost, HttpStatus.OK);
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
