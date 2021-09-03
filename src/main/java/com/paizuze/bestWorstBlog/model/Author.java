package com.paizuze.bestWorstBlog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paizuze.bestWorstBlog.dto.AuthorDTO;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
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

    public AuthorDTO toAuthorDTO(){
        AuthorDTO dto = new AuthorDTO();
        dto.setBalance(this.getBalance().toString());
        dto.setFirstName(this.getFirstName());
        dto.setLastName(this.getLastName());
        dto.setId(this.getId());
        return dto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Author author = (Author) obj;
        return author.getId().longValue() == this.getId().longValue();
    }

    @Override
    public int hashCode() {
        return this.getId() != null ? this.getId().hashCode() : 0;
    }

    public Set<BlogPost> getBlogPosts() {
        return blogPosts;
    }

    public void setBlogPosts(Set<BlogPost> blogPosts) {
        this.blogPosts = blogPosts;
    }

    public Set<Donation> getDonations() {
        return donations;
    }

    public void setDonations(Set<Donation> donations) {
        this.donations = donations;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
