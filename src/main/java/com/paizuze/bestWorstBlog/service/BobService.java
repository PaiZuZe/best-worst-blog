package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Bob;
import com.paizuze.bestWorstBlog.repository.BobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class BobService {

    @Autowired
    private BobRepository bobRepository;

    public Page<Bob> getPage(Pageable pageable) {
        return bobRepository.findAll(pageable);
    }

    public ResponseEntity<List<Bob>> getAll() {
        return new ResponseEntity<>(bobRepository.findAll(), HttpStatus.OK);
    }


    public ResponseEntity<Bob> getById(long id) {
        Optional<Bob> bob = bobRepository.findById(id);
        return bob.isPresent() ? new ResponseEntity<>(bob.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Bob> create(Bob new_bob) {
        new_bob = bobRepository.save(new_bob);
        return new ResponseEntity<>(new_bob, HttpStatus.CREATED);
    }

    public ResponseEntity<Bob> update(long id, Bob changed_bob) {
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

    public ResponseEntity<?> deleteById(long id) {
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
