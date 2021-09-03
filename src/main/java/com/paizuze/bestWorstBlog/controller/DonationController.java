package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.dto.DonationDTO;
import com.paizuze.bestWorstBlog.model.Donation;
import com.paizuze.bestWorstBlog.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/donate")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @GetMapping
    public ResponseEntity<List<DonationDTO>> getAll() {
        List<DonationDTO> resp = this.donationService.getAll();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDTO> get(@PathVariable Long id){
        DonationDTO resp = this.donationService.get(id);
        return resp != null ? new ResponseEntity<>(resp, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<DonationDTO> post(@RequestBody DonationDTO donation) {
        DonationDTO resp = donationService.post(donation.getAuthorId(), new Donation(donation));
        return resp != null ? new ResponseEntity<>(resp, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
