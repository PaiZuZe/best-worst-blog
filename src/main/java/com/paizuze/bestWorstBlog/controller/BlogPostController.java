package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogPost")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    @GetMapping("/pages")
    public ResponseEntity<Page<BlogPost>> getPage(Pageable pageable) {
        return blogPostService.getPage(pageable);
    }

    @GetMapping
    public ResponseEntity<List<BlogPost>> getAll() {
        return blogPostService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getById(@PathVariable long id) {
        return blogPostService.getById(id);
    }

    @PostMapping
    public ResponseEntity<BlogPost> create(@RequestBody BlogPost newBlogPost, @RequestParam("author_id") long author_id) {
        return blogPostService.create(newBlogPost, author_id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return blogPostService.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> putById(@PathVariable long id, @RequestBody BlogPost changed_blogPost) {
        return blogPostService.putById(id, changed_blogPost);
    }
}
