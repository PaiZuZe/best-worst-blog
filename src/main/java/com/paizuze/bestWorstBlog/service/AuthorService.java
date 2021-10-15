package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Page<Author> getPage(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public List<Author> findAuthorByFullName(String fullName) {
        return this.authorRepository.findAuthorByFullName(fullName);
    }

    public Author getById(long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.orElse(null);
    }

    public Set<BlogPost> getAuthorsBlogPosts(long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.map(Author::getBlogPosts).orElse(null);
    }

    public Author create(Author new_author) {
        return authorRepository.save(new_author);
    }

    public Author putById(long id, Author changed_author) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            changed_author.setId(author.get().getId());
            changed_author.setDonations(author.get().getDonations());
            changed_author.setBalance(author.get().getBalance());
            return authorRepository.save(changed_author);
        }
        return null;
    }

    public boolean deleteById(long id){
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean donate(long id, BigDecimal donation_amount){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            author.get().setBalance(author.get().getBalance().add(donation_amount));
            authorRepository.save(author.get());
            return true;
        }
        return false;
    }

}
