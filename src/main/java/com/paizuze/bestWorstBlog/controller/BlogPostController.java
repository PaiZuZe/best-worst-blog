package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.dto.BlogPostDTO;
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
    public ResponseEntity<Page<BlogPostDTO>> getPage(Pageable pageable) {
        return blogPostService.getPage(pageable);
    }

    @GetMapping
    public ResponseEntity<List<BlogPostDTO>> getAll() {
        return blogPostService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostDTO> getById(@PathVariable long id) {
        return blogPostService.getById(id);
    }

    @PostMapping
    public ResponseEntity<BlogPostDTO> create(@RequestBody BlogPostDTO newBlogPost) {
        return blogPostService.create(newBlogPost.getAuthorId(), newBlogPost.toBlogPost());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostDTO> putById(@PathVariable long id, @RequestBody BlogPostDTO changed_blogPost) {
        return blogPostService.putById(id, changed_blogPost.toBlogPost());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return blogPostService.deleteById(id);
    }

}
