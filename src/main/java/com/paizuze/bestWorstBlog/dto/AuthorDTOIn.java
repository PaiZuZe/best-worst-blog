package com.paizuze.bestWorstBlog.dto;


import com.paizuze.bestWorstBlog.model.Author;

public class AuthorDTOIn {
    private String lastName;
    private String firstName;
    private String balance = "0.0";

    public Author toAuthor() {
        return new Author(this.getFirstName(), this.getLastName(), this.getBalance());
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
