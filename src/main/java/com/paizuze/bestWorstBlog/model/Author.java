package com.paizuze.bestWorstBlog.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    private BigDecimal balance = new BigDecimal("0.0");

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
    private Set<BlogPost> blogPosts;

    public Author() {
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
