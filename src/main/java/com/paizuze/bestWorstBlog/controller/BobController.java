package com.paizuze.bestWorstBlog.controller;


import com.paizuze.bestWorstBlog.model.Bob;
import com.paizuze.bestWorstBlog.repository.BobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bob")
public class BobController {

    @Autowired
    private BobRepository bobRepository;

    @GetMapping
    public ResponseEntity<List<Bob>> getAll() {
        return new ResponseEntity<>(bobRepository.findAll(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Bob> getById(@PathVariable long id) {
        Optional<Bob> bob = bobRepository.findById(id);
        return bob.isPresent() ? new ResponseEntity<>(bob.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Bob> post(@RequestBody Bob new_bob) {
        new_bob = bobRepository.save(new_bob);
        return new ResponseEntity<>(new_bob, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bob> putByID(@PathVariable long id, @RequestBody Bob changed_bob){
        Optional<Bob> bob = bobRepository.findById(id);
        if (bob.isPresent()) {
            changed_bob.setId(bob.get().getId());
            changed_bob = bobRepository.save(changed_bob);
            return new ResponseEntity<>(changed_bob, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        Optional<Bob> bob = bobRepository.findById(id);
        if (bob.isPresent()) {
            bobRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
