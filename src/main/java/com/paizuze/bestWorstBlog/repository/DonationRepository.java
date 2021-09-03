package com.paizuze.bestWorstBlog.repository;

import com.paizuze.bestWorstBlog.model.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends JpaRepository<Donation, Long> {
}
