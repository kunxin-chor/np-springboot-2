package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.models.Product;
import com.example.demo.repo.ProductRepo;

@Service
public class ProductServices {

    private final ProductRepo productRepo;

      public ProductServices(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public Optional<Product> findById(Long id) {
        return productRepo.findById(id);
    }

    public List<Product> findAllWithCategories() {
        return productRepo.findAllWithCategories();
    }
    
}
