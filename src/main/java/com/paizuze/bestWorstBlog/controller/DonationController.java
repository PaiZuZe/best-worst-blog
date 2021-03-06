package com.paizuze.bestWorstBlog.controller;

import com.paizuze.bestWorstBlog.dto.DonationDTO;
import com.paizuze.bestWorstBlog.model.Donation;
import com.paizuze.bestWorstBlog.service.DonationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/donate")
public class DonationController {
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public ResponseEntity<List<DonationDTO>> getAll() {
        List<Donation> resp = this.donationService.getAll();
        return new ResponseEntity<>(resp.stream().map(DonationDTO::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDTO> get(@PathVariable Long id){
        Donation resp = this.donationService.get(id);
        return resp != null ? new ResponseEntity<>(new DonationDTO(resp), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<DonationDTO> post(@RequestBody DonationDTO donation) {
        Donation resp = donationService.post(donation.getAuthorId(), new Donation(donation));
        return resp != null ? new ResponseEntity<>(new DonationDTO(resp), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
