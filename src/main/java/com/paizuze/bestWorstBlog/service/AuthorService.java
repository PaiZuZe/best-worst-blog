package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.dto.AuthorDTO;
import com.paizuze.bestWorstBlog.dto.BlogPostDTO;
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
import java.util.stream.Collectors;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Page<AuthorDTO> getPage(Pageable pageable) {
        return authorRepository.findAll(pageable).map(Author::toAuthorDTO);
    }

    public List<AuthorDTO> getAll() {
        return authorRepository.findAll().stream().map(Author::toAuthorDTO).collect(Collectors.toList());
    }

    public AuthorDTO getById(long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.isPresent() ? author.get().toAuthorDTO() : null;
    }

    public Set<BlogPostDTO> getAuthorsBlogPosts(long id) {
        Optional<Author> author = authorRepository.findById(id);
        return author.isPresent() ? author.get().getBlogPosts().stream().map(BlogPost::toBlogPostDTO).collect(Collectors.toSet()) : null;
    }

    public AuthorDTO create(Author new_author) {
        return authorRepository.save(new_author).toAuthorDTO();
    }

    public AuthorDTO putById(long id, Author changed_author) {
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            changed_author.setId(author.get().getId());
            changed_author.setBalance(author.get().getBalance());
            return authorRepository.save(changed_author).toAuthorDTO();
        }
        else {
            return null;
        }
    }

    public boolean deleteById(long id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            authorRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean donate(long id, BigDecimal donation_amount){
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            author.get().setBalance(author.get().getBalance().add(donation_amount));
            authorRepository.save(author.get());
            return true;
        }
        else {
            return false;
        }
    }
}
