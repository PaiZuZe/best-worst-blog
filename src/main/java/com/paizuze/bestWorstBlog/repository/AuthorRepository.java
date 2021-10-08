package com.paizuze.bestWorstBlog.repository;

import com.paizuze.bestWorstBlog.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query(value = "SELECT * FROM authors WHERE CONCAT(first_name, ' ', last_name)  LIKE :fullname%", nativeQuery = true)
    List<Author> findAuthorByFullName(@Param("fullname") String fullName);
}
