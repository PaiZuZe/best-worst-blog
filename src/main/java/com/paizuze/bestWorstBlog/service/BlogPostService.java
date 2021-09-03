package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.dto.BlogPostDTO;
import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import com.paizuze.bestWorstBlog.repository.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final AuthorRepository authorRepository;

    public BlogPostService(AuthorRepository authorRepository, BlogPostRepository blogPostRepository) {
        this.authorRepository = authorRepository;
        this.blogPostRepository = blogPostRepository;
    }

    public Page<BlogPostDTO> getPage(Pageable pageable) {
        return blogPostRepository.findAll(pageable).map(BlogPost::toBlogPostDTO);
    }

    public List<BlogPostDTO> getAll() {
        return blogPostRepository.findAll().stream().map(BlogPost::toBlogPostDTO).collect(Collectors.toList());
    }

    public BlogPostDTO getById(long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.isPresent() ? blogPost.get().toBlogPostDTO() : null;
    }

    public BlogPostDTO create(long author_id, BlogPost newBlogPost) {
        Optional<Author> author = authorRepository.findById(author_id);
        if (author.isEmpty()) {
            return null;
        }
        newBlogPost.setAuthor(author.get());
        return blogPostRepository.save(newBlogPost).toBlogPostDTO();
    }

    public BlogPostDTO putById(long id, BlogPost changed_blogPost) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            changed_blogPost.setId(blogPost.get().getId());
            changed_blogPost.setAuthor(blogPost.get().getAuthor());
            return blogPostRepository.save(changed_blogPost).toBlogPostDTO();
        }
        else {
            return null;
        }
    }

    public boolean deleteById(long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            blogPostRepository.deleteById(id);
            return true;
        }
        else {
            return false;
        }
    }
}
