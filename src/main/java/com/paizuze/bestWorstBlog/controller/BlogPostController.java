package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.dto.BlogPostDTO;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.service.BlogPostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/blogPost")
public class BlogPostController {

    private final BlogPostService blogPostService;

    public BlogPostController(BlogPostService blogPostService) {
        this.blogPostService = blogPostService;
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<BlogPostDTO>> getPage(Pageable pageable) {
        Page<BlogPost> resp = blogPostService.getPage(pageable);
        return new ResponseEntity<>(resp.map(BlogPostDTO::new), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BlogPostDTO>> getAll() {
        List<BlogPost> resp = blogPostService.getAll();
        return new ResponseEntity<>(resp.stream().map(BlogPostDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPostDTO> getById(@PathVariable long id) {
        BlogPost resp = blogPostService.getById(id);
        return resp != null ? new ResponseEntity<>(new BlogPostDTO(resp), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<BlogPostDTO> create(@RequestBody BlogPostDTO newBlogPost) {
        try {
            BlogPost resp = blogPostService.create(newBlogPost.getAuthorId(), new BlogPost(newBlogPost));
            return resp != null ? new ResponseEntity<>(new BlogPostDTO(resp), HttpStatus.CREATED) : new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogPostDTO> putById(@PathVariable long id, @RequestBody BlogPostDTO changed_blogPost) {
        try {
            BlogPost resp = blogPostService.putById(id, new BlogPost(changed_blogPost));
            return resp != null ? new ResponseEntity<>(new BlogPostDTO(resp), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
