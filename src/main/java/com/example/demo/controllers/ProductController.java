package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.repo.ProductRepo;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ProductController {

    private final ProductRepo productRepo;

    @Autowired
    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        List<Product> products = productRepo.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }

    @GetMapping("/products/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        return "products/details";
    }

    @GetMapping("/products/create")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "products/create";
    }

    @PostMapping("/products/create")
    public String createProduct(@Valid @ModelAttribute Product newProduct, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error in product submitted data");
            return "products/create";
            
        }
        System.out.println("Valid product received");
        productRepo.save(newProduct);
        return "redirect:/products";
    }

    @GetMapping("/products/{id}/edit")
    public String showUpdateProduct(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id).orElseThrow( () -> new RuntimeException("Product not found"));
        model.addAttribute(product);
        return "products/edit";
        
    }

    @PostMapping("/products/{id}/edit")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute Product product, 
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "products/edit";
        }
        product.setId(id); // Ensure we're updating the correct product
        productRepo.save(product);
        return "redirect:/products";
    }
}
