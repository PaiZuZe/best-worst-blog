package com.paizuze.bestWorstBlog.dto;


import com.paizuze.bestWorstBlog.model.Author;

public class AuthorDTOIn {
    private String lastName;
    private String firstName;

    public Author toAuthor() {
        return new Author(this.getFirstName(), this.getLastName());
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
