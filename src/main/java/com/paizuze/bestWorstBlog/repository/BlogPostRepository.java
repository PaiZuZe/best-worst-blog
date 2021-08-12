package com.paizuze.bestWorstBlog.repository;

import com.paizuze.bestWorstBlog.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends JpaRepository<BlogPost, Long> {
}
