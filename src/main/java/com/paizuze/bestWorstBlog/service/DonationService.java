package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.Donation;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import com.paizuze.bestWorstBlog.repository.DonationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DonationService {
    private final DonationRepository donationRepository;
    private final AuthorRepository authorRepository;
    private final AuthorService authorService;

    public DonationService(DonationRepository donationRepository, AuthorRepository authorRepository, AuthorService authorService) {
        this.donationRepository = donationRepository;
        this.authorRepository = authorRepository;
        this.authorService = authorService;
    }

    public List<Donation> getAll() {
        return this.donationRepository.findAll();
    }

    public Donation get(Long id) {
        Optional<Donation> donation = this.donationRepository.findById(id);
        return donation.orElse(null);
    }

    public Donation post(Long authorId, Donation donation) {
        Optional<Author> author = this.authorRepository.findById(authorId);
        if (author.isEmpty()) {
            return null;
        }
        authorService.donate(authorId, donation.getAmount());
        donation.setAuthor(author.get());
        return this.donationRepository.save(donation);
    }
}
