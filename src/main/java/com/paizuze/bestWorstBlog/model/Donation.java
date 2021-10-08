package com.paizuze.bestWorstBlog.model;

import com.paizuze.bestWorstBlog.dto.DonationDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
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
        this.setAmount(dto.getDonationAmount().abs());
    }
}
