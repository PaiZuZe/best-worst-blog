package com.paizuze.bestWorstBlog.model;

import com.paizuze.bestWorstBlog.dto.DonationDTO;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "donations")
public class Donation implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal amount;

    @ManyToOne
    private Author author;

    public Donation() {
    }

    public Donation(DonationDTO dto) {
        this.setAmount(dto.getDonationAmount());
    }

    public DonationDTO toDTO() {
        DonationDTO dto = new DonationDTO();
        dto.setDonationAmount(this.getAmount());
        dto.setAuthorId(this.getAuthor().getId());
        dto.setId(this.getId());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Donation newDonation = (Donation) obj;
        return newDonation.getId().longValue() == this.getId().longValue();
    }
}
