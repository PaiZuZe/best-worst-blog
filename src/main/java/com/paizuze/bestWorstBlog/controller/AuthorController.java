package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/author")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping("/pages")
    public ResponseEntity<Page<Author>> getPage(Pageable pageable) {
        return new ResponseEntity<>(authorRepository.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getById(@PathVariable long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.isPresent() ? new ResponseEntity<>(author.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Author> post(@RequestBody Author new_author) {
        new_author = authorRepository.save(new_author);
        return new ResponseEntity<>(new_author, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> putById(@PathVariable long id, @RequestBody Author changed_author) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            changed_author.setId(author.get().getId());
            changed_author = authorRepository.save(changed_author);
            return new ResponseEntity<>(changed_author, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
