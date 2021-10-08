package com.paizuze.bestWorstBlog.dto;

import com.paizuze.bestWorstBlog.model.Donation;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class DonationDTO {
    Long id;
    Long authorId;
    BigDecimal donationAmount;

    public DonationDTO(){}

    public DonationDTO(Donation donation) {
        this();
        this.setId(donation.getId());
        this.setAuthorId(donation.getAuthor().getId());
        this.setDonationAmount(donation.getAmount());
    }
}
