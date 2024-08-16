package com.example.demo.controllers;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
  

@Controller
public class HomeController {          
    @GetMapping("/")
    public String helloWorld() {
        return "home";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @GetMapping("/test-templates")
    public String testModel(Map<String, Object> model) {
        model.put("message", "Goodbye World");
        return "test-model";
    }
}