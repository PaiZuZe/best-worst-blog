package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.dto.DonationDTO;
import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.Donation;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import com.paizuze.bestWorstBlog.repository.DonationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<DonationDTO> getAll() {
        return this.donationRepository.findAll().stream().map(Donation::toDTO).collect(Collectors.toList());
    }

    public DonationDTO get(Long id) {
        Optional<Donation> donation = this.donationRepository.findById(id);
        return donation.isPresent() ? donation.get().toDTO() : null;
    }

    public DonationDTO post(Long authorId, Donation donation) {
        Optional<Author> author = this.authorRepository.findById(authorId);
        if (author.isEmpty()) {
            return null;
        }
        authorService.donate(authorId, donation.getAmount());
        donation.setAuthor(author.get());
        return this.donationRepository.save(donation).toDTO();
    }
}
