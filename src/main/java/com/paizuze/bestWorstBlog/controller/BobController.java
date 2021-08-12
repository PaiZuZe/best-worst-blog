package com.paizuze.bestWorstBlog.controller;


import com.paizuze.bestWorstBlog.model.Bob;
import com.paizuze.bestWorstBlog.service.BobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bob")
public class BobController {

    @Autowired
    private BobService bobService;

    @GetMapping(value ="/pages")
    public Page<Bob> getPage(Pageable pageable) {
        return bobService.getPage(pageable);
    }

    @GetMapping
    public ResponseEntity<List<Bob>> getAll() {
        return bobService.getAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Bob> getById(@PathVariable long id) {
        return bobService.getById(id);
    }

    @PostMapping
    public ResponseEntity<Bob> post(@RequestBody Bob new_bob) {
        return bobService.create(new_bob);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Bob> putByID(@PathVariable long id, @RequestBody Bob changed_bob){
        return bobService.update(id, changed_bob);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable long id) {
        return bobService.deleteById(id);
    }
}
