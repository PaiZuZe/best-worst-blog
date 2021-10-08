package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.Donation;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import com.paizuze.bestWorstBlog.repository.DonationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class DonationServiceTest {
    DonationRepository donationRepository;
    AuthorRepository authorRepository;
    AuthorService authorService;

    @InjectMocks
    DonationService donationService;

    private final Long ID_NOT_FOUND = 100L;
    private final Author author = new Author("Bob", "dos Santos");
    private final Donation donation = new Donation();
    private final Donation newDonation = new Donation();

    @BeforeAll
    void setUp() {
        this.author.setId(1L);
        this.donation.setId(2L);
        this.newDonation.setId(3L);
        this.donation.setAmount(BigDecimal.valueOf(10L));
        this.donation.setAuthor(this.author);
        this.newDonation.setAmount(BigDecimal.valueOf(100L));
        this.author.setDonations(new HashSet<>(List.of(donation)));
        this.donationRepository = mock(DonationRepository.class);
        this.authorRepository = mock(AuthorRepository.class);
        this.authorService = mock(AuthorService.class);

        Mockito.when(donationRepository.findAll()).thenReturn(List.of(donation));
        Mockito.when(donationRepository.findById(this.ID_NOT_FOUND)).thenReturn(Optional.empty());
        Mockito.when(donationRepository.findById(2L)).thenReturn(Optional.of(this.donation));
        Mockito.when(donationRepository.save(this.newDonation)).thenReturn(this.newDonation);
        Mockito.when(authorRepository.findById(1L)).thenReturn(Optional.of(this.author));
        Mockito.when(authorRepository.save(this.author)).thenReturn(this.author);
        Mockito.when(authorService.donate(1L, this.newDonation.getAmount())).thenReturn(true);
    }

    @Test
    void testGetAll() {
        List<Donation> resp = this.donationService.getAll();
        Assertions.assertEquals(1, resp.size());
    }

    @Test
    void testGet() {
        Donation resp = this.donationService.get(2L);
        Assertions.assertEquals(1L, resp.getAuthor().getId());
        Assertions.assertEquals(BigDecimal.valueOf(10L), resp.getAmount());
    }

    @Test
    void testGetIdNotFound() {
        Donation resp = this.donationService.get(this.ID_NOT_FOUND);
        Assertions.assertNull(resp);
    }

    @Test
    void testPost() {
        Donation resp = this.donationService.post(1L, this.newDonation);
        Assertions.assertEquals(this.author.getId(), resp.getAuthor().getId());
        verify(donationRepository, times(1)).save(this.newDonation);
    }

    @Test
    void testPostIdNotFound() {
        Donation resp = this.donationService.post(this.ID_NOT_FOUND, new Donation());
        Assertions.assertNull(resp);
    }
}
