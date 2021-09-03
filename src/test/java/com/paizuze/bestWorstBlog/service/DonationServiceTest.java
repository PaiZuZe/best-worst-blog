package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.dto.DonationDTO;
import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.Donation;
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

import static org.mockito.Mockito.mock;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class DonationServiceTest {
    DonationRepository donationRepository;

    @InjectMocks
    DonationService donationService;

    private final Long ID_NOT_FOUND = 100L;
    private final Author author = new Author("Bob", "dos Santos");
    private final Donation donation = new Donation();

    @BeforeAll
    void setUp() {
        this.author.setId(1L);
        this.donation.setId(2L);
        this.donation.setAmount(BigDecimal.valueOf(10L));
        this.donation.setAuthor(author);
        this.author.setDonations(new HashSet<>(List.of(donation)));
        this.donationRepository = mock(DonationRepository.class);

        Mockito.when(donationRepository.findAll()).thenReturn(List.of(donation));
        Mockito.when(donationRepository.findById(this.ID_NOT_FOUND)).thenReturn(Optional.empty());
        Mockito.when(donationRepository.findById(2L)).thenReturn(Optional.of(this.donation));
    }

    @Test
    void testGetAll() {
        List<DonationDTO> resp = this.donationService.getAll();
        Assertions.assertEquals(1, resp.size());
    }

    @Test
    void testGet() {
        DonationDTO resp = this.donationService.get(2L);
        Assertions.assertEquals(1L, resp.getAuthorId());
        Assertions.assertEquals(BigDecimal.valueOf(10L), resp.getDonationAmount());
    }

    @Test
    void testGetIdNotFound() {
        DonationDTO resp = this.donationService.get(this.ID_NOT_FOUND);
        Assertions.assertNull(resp);
    }
}
