package com.paizuze.bestWorstBlog.service;

import com.paizuze.bestWorstBlog.model.Author;
import com.paizuze.bestWorstBlog.model.BlogPost;
import com.paizuze.bestWorstBlog.repository.AuthorRepository;
import com.paizuze.bestWorstBlog.repository.BlogPostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogPostService {
    private final BlogPostRepository blogPostRepository;
    private final AuthorRepository authorRepository;

    public BlogPostService(AuthorRepository authorRepository, BlogPostRepository blogPostRepository) {
        this.authorRepository = authorRepository;
        this.blogPostRepository = blogPostRepository;
    }

    public Page<BlogPost> getPage(Pageable pageable) {
        return blogPostRepository.findAll(pageable);
    }

    public List<BlogPost> getAll() {
        return blogPostRepository.findAll();
    }

    public BlogPost getById(long id) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        return blogPost.orElse(null);
    }

    public BlogPost create(long author_id, BlogPost newBlogPost) {
        Optional<Author> author = authorRepository.findById(author_id);
        if (author.isPresent()) {
            newBlogPost.setAuthor(author.get());
            return blogPostRepository.save(newBlogPost);
        }
        return null;
    }

    public BlogPost putById(long id, BlogPost changed_blogPost) {
        Optional<BlogPost> blogPost = blogPostRepository.findById(id);
        if (blogPost.isPresent()) {
            changed_blogPost.setId(blogPost.get().getId());
            changed_blogPost.setAuthor(blogPost.get().getAuthor());
            return blogPostRepository.save(changed_blogPost);
        }
        return null;
    }

    public boolean deleteById(long id) {
        if (blogPostRepository.existsById(id)) {
            blogPostRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
