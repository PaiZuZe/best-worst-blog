package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.dto.DonationDTO;
import com.paizuze.bestWorstBlog.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/donate")
public class DonationController {
    @Autowired
    private AuthorService authorService;

    @PostMapping
    public ResponseEntity<?> post(@RequestBody DonationDTO donation) {
        boolean resp = authorService.donate(donation.getAuthorId(), donation.getDonationAmount());
        return resp ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
