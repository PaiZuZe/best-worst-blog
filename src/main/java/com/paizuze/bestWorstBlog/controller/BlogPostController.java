package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.dto.BlogPostDTO;
import com.paizuze.bestWorstBlog.service.BlogPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
        Page<BlogPostDTO> resp = blogPostService.getPage(pageable);
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BlogPostDTO>> getAll() {
        List<BlogPostDTO> resp = blogPostService.getAll();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostDTO> getById(@PathVariable long id) {
        BlogPostDTO resp = blogPostService.getById(id);
        return resp != null ? new ResponseEntity<>(resp, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<BlogPostDTO> create(@RequestBody BlogPostDTO newBlogPost) {
        try {
            BlogPostDTO resp = blogPostService.create(newBlogPost.getAuthorId(), newBlogPost.toBlogPost());
            return resp != null ? new ResponseEntity<>(resp, HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostDTO> putById(@PathVariable long id, @RequestBody BlogPostDTO changed_blogPost) {
        try {
            BlogPostDTO resp = blogPostService.putById(id, changed_blogPost.toBlogPost());
            return resp != null ? new ResponseEntity<>(resp, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        boolean resp = blogPostService.deleteById(id);
        return resp ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
