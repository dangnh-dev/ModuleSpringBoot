package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserModel;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/sign-up")
    public String signUpPage(Model model){
        model.addAttribute("user", new UserDTO());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String addNewUser(UserDTO userDTO, RedirectAttributes redirectAttributes){
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        UserModel userModel = new UserModel().toModel(userDTO);
        boolean addUser = userService.addNewUser(userModel);
        if (!addUser){
            redirectAttributes.addFlashAttribute("message", "Email exist");
            return "redirect:/sign-up";
        }
        return "redirect:/login";
    }
}
