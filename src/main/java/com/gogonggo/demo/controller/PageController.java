package com.gogonggo.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;  // <- ต้องเพิ่มบรรทัดนี้

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
public class PageController {

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "Index");
        return "redirect:/login";  // ทำ redirect ไปยัง /login
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "views/login";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "views/home";
    }

    @GetMapping("/time")
    public String time(Model model) {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String formattedTime = currentTime.format(formatter);
        model.addAttribute("currentTime", formattedTime);
        model.addAttribute("title", "Time Page");
        return "views/time";
    }

    @GetMapping("/showForm")
    public String showForm(Model model) {
        model.addAttribute("title", "Home Page");
        return "views/01-example/showForm";
    }
    @GetMapping("/processForm")
    public String processForm(Model model) {
        model.addAttribute("title", "Home Page");
        return "views/01-example/processForm";
    }

    @GetMapping("/showFormV2")
    public String showFormV2(Model model) {
        model.addAttribute("title", "Home Page");
        return "views/02-showFormV2/showForm";
    }
    @GetMapping("/processFormV2")
    public String processFormV2(HttpServletRequest request,Model model) {
        String theName = request.getParameter("studentName");
        theName = theName.toUpperCase();
        String result = "Yo! " + theName;
        model.addAttribute("message", result);
        return "views/02-showFormV2/processForm";
    }        
}