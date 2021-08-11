package com.paizuze.bestWorstBlog.repository;

import com.paizuze.bestWorstBlog.model.Bob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BobRepository extends JpaRepository<Bob, Long> {
}
