package com.paizuze.bestWorstBlog.repository;

import com.paizuze.bestWorstBlog.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
