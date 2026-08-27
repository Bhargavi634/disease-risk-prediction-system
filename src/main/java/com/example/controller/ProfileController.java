package com.example.controller;

import com.example.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;

@Controller
public class ProfileController {
    @GetMapping("/profile")
    public String profile(HttpSession session, Model m){
        User user = (User) session.getAttribute("user");
        if(user==null) return "redirect:/login";
        m.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/diabetes")
    public String diabetesForm(HttpSession session){
        if(session.getAttribute("user")==null) return "redirect:/login";
        return "diabetes";
    }

    @GetMapping("/heart")
    public String heartForm(HttpSession session){
        if(session.getAttribute("user")==null) return "redirect:/login";
        return "heart";
    }
}
