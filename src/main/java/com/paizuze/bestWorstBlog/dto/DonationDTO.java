package com.paizuze.bestWorstBlog.dto;

import java.math.BigDecimal;

public class DonationDTO {
    BigDecimal donationAmount;
    Long authorId;

    public BigDecimal getDonationAmount() {
        return donationAmount;
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
