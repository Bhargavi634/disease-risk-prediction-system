package com.example.controller;

import com.example.model.User;
import com.example.repo.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;

@Controller
public class AuthController {
    private final UserRepository userRepo;
    public AuthController(UserRepository r){this.userRepo=r;}

    @GetMapping("/")
    public String home(){ return "redirect:/login"; }

    @GetMapping("/register")
    public String registerForm(){ return "register"; }

    @PostMapping("/register")
    public String register(User user, Model m){
        if(userRepo.findByUsername(user.getUsername()).isPresent()){
            m.addAttribute("error","Username taken");
            return "register";
        }
        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String loginForm(){ return "login"; }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model m){
        var opt = userRepo.findByUsername(username);
        if(opt.isPresent() && opt.get().getPassword().equals(password)){
            session.setAttribute("user", opt.get());
            return "redirect:/profile";
        }
        m.addAttribute("error","Invalid credentials");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){ session.invalidate(); return "redirect:/login"; }
}
