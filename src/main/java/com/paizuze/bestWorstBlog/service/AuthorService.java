package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    public ResponseEntity<Page<Author>> getPage(Pageable pageable) {
        return new ResponseEntity<>(authorRepository.findAll(pageable), HttpStatus.OK);
    }

    public ResponseEntity<List<Author>> getAll() {
        return new ResponseEntity<>(authorRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Author> getById(long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.isPresent() ? new ResponseEntity<>(author.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Author> post(Author new_author) {
        new_author = authorRepository.save(new_author);
        return new ResponseEntity<>(new_author, HttpStatus.CREATED);
    }

    public ResponseEntity<Author> putById(long id, Author changed_author) {
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

    public ResponseEntity<?> deleteById(long id){
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
