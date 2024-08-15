package com.example.demo.controllers;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
  

@Controller
public class HomeController {          
    @GetMapping("/")
    @ResponseBody
    public String helloWorld() {
        return "<h1>Hello World</h1>";
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