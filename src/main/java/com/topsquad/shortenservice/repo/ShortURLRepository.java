package com.topsquad.shortenservice.repo;

import com.topsquad.shortenservice.entity.ShortURL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShortURLRepository extends JpaRepository<ShortURL, Integer> {
    ShortURL findByShortCode(String code);
    List<ShortURL> findAllByCreatedById(Integer created_id);
}
