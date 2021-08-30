package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.dto.AuthorDTOIn;
import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/pages")
    public ResponseEntity<Page<Author>> getPage(Pageable pageable) {
        return authorService.getPage(pageable);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return authorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable long id) {
        return authorService.getById(id);
    }

    @GetMapping("/{id}/blogPosts")
    public ResponseEntity<Set<BlogPost>> getAuthorsBlogPosts(@PathVariable long id) {
        return authorService.getAuthorsBlogPosts(id);
    }

    @PostMapping
    public ResponseEntity<Author> post(@RequestBody AuthorDTOIn new_author) {
        return authorService.create(new_author.toAuthor());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> putById(@PathVariable long id, @RequestBody AuthorDTOIn changed_author) {
        return authorService.putById(id, changed_author.toAuthor());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return authorService.deleteById(id);
    }

    @PostMapping("/{id}/donate")
    public ResponseEntity<?> donate(@PathVariable long id, @RequestParam("donation_amount") BigDecimal donationAmount) {
        return authorService.donate(id, donationAmount);
    }
}
