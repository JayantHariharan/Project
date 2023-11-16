package com.lucifer.rankTest.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    // This is a simple fixed username and password for demonstration purposes
    

   

    // Add additional mappings for admin dashboard and other functionalities

    @RequestMapping("/dashboard")
    public String adminDashboard() {
        // Logic for displaying admin dashboard
        return "admin/dashboard";
    }
}
