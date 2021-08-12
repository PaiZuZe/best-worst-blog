package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.BlogPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/blogPost")
public class BlogPostController {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @GetMapping("/pages")
    public ResponseEntity<Page<BlogPost>> getPage(Pageable pageable) {
        return new ResponseEntity<>(blogPostRepository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BlogPost>> getAll() {
        return new ResponseEntity<>(blogPostRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getById(@PathVariable long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.isPresent() ? new ResponseEntity<>(blogPost.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<BlogPost> createBlogPost(@RequestBody BlogPost newBlogPost) {
        newBlogPost = blogPostRepository.save(newBlogPost);
        return new ResponseEntity<>(newBlogPost, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            blogPostRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> putById(@PathVariable long id, @RequestBody BlogPost changed_blogPost) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            changed_blogPost.setId(blogPost.get().getId());
            return new ResponseEntity<>(changed_blogPost, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
