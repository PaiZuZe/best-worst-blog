package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.dto.BlogPostDTOIn;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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
    public ResponseEntity<BlogPost> create(@RequestParam("author_id") long author_id, @RequestBody BlogPostDTOIn newBlogPost) {
        return blogPostService.create(author_id, newBlogPost.toBlogPost());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPost> putById(@PathVariable long id, @RequestBody BlogPostDTOIn changed_blogPost) {
        return blogPostService.putById(id, changed_blogPost.toBlogPost());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return blogPostService.deleteById(id);
    }

}
