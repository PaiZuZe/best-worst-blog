package com.paizuze.bestWorstBlog.dto;

import java.math.BigDecimal;

public class DonationDTO {
    Long id;
    Long authorId;
    BigDecimal donationAmount;

    public BigDecimal getDonationAmount() {
        return donationAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDonationAmount(BigDecimal donationAmount) {
        this.donationAmount = donationAmount;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
