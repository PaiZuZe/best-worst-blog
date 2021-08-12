package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<Author> post(@RequestBody Author new_author) {
        return authorService.post(new_author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> putById(@PathVariable long id, @RequestBody Author changed_author) {
        return authorService.putById(id, changed_author);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        return authorService.deleteById(id);
    }
}
