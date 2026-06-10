package com.example.demo.controller;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegCont {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, BindingResult bindingResult, Model model){
        if(bindingResult.hasFieldErrors()){
            return "register";
        }
        User existing=userService.findByEmail(user.getEmail());
        if (existing != null) {
            model.addAttribute("errorMessage","Email already exists");
            return "register";
        }
        userService.save(user);

        return "registerSuccess";
    }

}
