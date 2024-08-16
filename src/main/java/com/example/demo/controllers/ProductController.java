package com.example.demo.controllers;

import com.example.demo.models.Product;
import com.example.demo.models.Tag;
import com.example.demo.repo.CategoryRepo;
import com.example.demo.repo.ProductRepo;
import com.example.demo.repo.TagRepo;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ProductController {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final TagRepo tagRepo;

    @Autowired
    public ProductController(ProductRepo productRepo, CategoryRepo categoryRepo, TagRepo tagRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
        this.tagRepo = tagRepo;
    }

    @GetMapping("/products")
    public String listProducts(Model model) {
        // List<Product> products = productRepo.findAll();
        List<Product> products = productRepo.findAllWithCategories();
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
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("allTags", tagRepo.findAll());
        return "products/create";
    }

    @PostMapping("/products/create")
    public String createProduct(@Valid @ModelAttribute Product newProduct, 
            @RequestParam(required=false) List<Long> tagIds,
            BindingResult bindingResult, Model model,
            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            System.out.println("Error in product submitted data");
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("allTags", tagRepo.findAll());
            return "products/create";
            
        }

        // if tags are provided
        if (tagIds != null) {
            Set<Tag> tags = new HashSet<>(tagRepo.findAllById(tagIds));
            newProduct.setTags(tags);            
        }

        // System.out.println("Valid product received");
        redirectAttributes.addFlashAttribute("message", "New product has been added successfully");
        productRepo.save(newProduct);
        return "redirect:/products";
    }

    @GetMapping("/products/{id}/edit")
    public String showUpdateProduct(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id).orElseThrow( () -> new RuntimeException("Product not found"));
        model.addAttribute(product);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("allTags", tagRepo.findAll());
        return "products/edit";
        
    }

    @PostMapping("/products/{id}/edit")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute Product product, 
                                @RequestParam(required=false) List<Long> tagIds,
                                RedirectAttributes redirectAttributes,
                                BindingResult bindingResult, Model model) {

        System.out.println(product.getCategory());

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryRepo.findAll());
            model.addAttribute("allTags", productRepo.findAll());
            return "products/edit";
        }
        
        // if there are selected tags
        if (tagIds != null && !tagIds.isEmpty()) {
            Set<Tag> tags = new HashSet<>(tagRepo.findAllById(tagIds));
            product.setTags(tags);
        } else {
            product.getTags().clear();
        }


        product.setId(id); // Ensure we're updating the correct product
        productRepo.save(product);
        redirectAttributes.addFlashAttribute("message", "Product has been edited");
        return "redirect:/products";
    }

    @GetMapping("/products/{id}/delete")
    public String showDeleteProductForm(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        model.addAttribute("product", product);
        return "products/delete";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "redirect:/products";
    }
}
