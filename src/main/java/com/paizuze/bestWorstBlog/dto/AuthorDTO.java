package com.paizuze.bestWorstBlog.dto;


import com.paizuze.bestWorstBlog.model.Author;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class AuthorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private BigDecimal balance;

    public AuthorDTO(){}

    public AuthorDTO(Author author) {
        this();
        this.setId(author.getId());
        this.setFirstName(author.getFirstName());
        this.setLastName(author.getLastName());
        this.setBalance(author.getBalance());
    }
}
