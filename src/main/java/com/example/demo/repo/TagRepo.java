package com.example.demo.repo;

import com.example.demo.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepo extends JpaRepository<Tag, Long> {
    
    // Find a tag by its name
    Optional<Tag> findByName(String name);
    
    // Find all tags with names containing the given string (case-insensitive)
    List<Tag> findByNameContainingIgnoreCase(String name);
    
    // Check if a tag with the given name exists
    boolean existsByNameIgnoreCase(String name);
    
    // Find all tags sorted by name
    List<Tag> findAllByOrderByNameAsc();
    
    // Find tags associated with a specific product ID
    List<Tag> findByProductsId(Long productId);
}