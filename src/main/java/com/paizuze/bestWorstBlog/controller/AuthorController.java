package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.dto.AuthorDTO;
import com.paizuze.bestWorstBlog.dto.BlogPostDTO;
import com.paizuze.bestWorstBlog.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/author")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<AuthorDTO>> getPage(Pageable pageable) {
        return new ResponseEntity<>(authorService.getPage(pageable), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAll() {
        return new ResponseEntity<>(authorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getById(@PathVariable long id) {
        AuthorDTO resp = authorService.getById(id);
        return resp != null ? new ResponseEntity<>(resp, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}/blogPosts")
    public ResponseEntity<Set<BlogPostDTO>> getAuthorsBlogPosts(@PathVariable long id) {
        Set<BlogPostDTO> resp =  authorService.getAuthorsBlogPosts(id);
        return resp != null ? new ResponseEntity<>(resp, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> post(@RequestBody AuthorDTO new_author) {
        try {
            AuthorDTO resp = authorService.create(new_author.toAuthor());
            return new ResponseEntity<>(resp, HttpStatus.CREATED);
        }
        catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorDTO> putById(@PathVariable long id, @RequestBody AuthorDTO changed_author) {
        try {
            AuthorDTO resp = authorService.putById(id, changed_author.toAuthor());
            return resp != null ? new ResponseEntity<>(resp, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (Exception err) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id){
        boolean resp = authorService.deleteById(id);
        return resp ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
