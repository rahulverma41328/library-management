package com.rahul.libraryManagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public class PagesController {

    @GetMapping("/loan-details")
    public String showLoanPage(Model model){
        return "loan-book";
    }
}
