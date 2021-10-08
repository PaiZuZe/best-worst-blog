package com.paizuze.bestWorstBlog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paizuze.bestWorstBlog.dto.AuthorDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "authors", uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"})})
public class Author implements Serializable {

    @Serial
    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    private BigDecimal balance;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Set<BlogPost> blogPosts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Set<Donation> donations;

    public Author() {
        this.setBalance(new BigDecimal("0.0"));
        this.setBlogPosts(new HashSet<>());
        this.setDonations(new HashSet<>());
    }

    public Author(String firstName, String lastName) {
        this();
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public Author(String firstName, String lastName, String balance) {
        this(firstName, lastName);
        this.setBalance(new BigDecimal(balance));
    }

    public Author(AuthorDTO authorDTO) {
        this();
        this.setFirstName(authorDTO.getFirstName());
        this.setLastName(authorDTO.getLastName());
    }
}
