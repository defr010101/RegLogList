package com.example.regloglist.controller;

import com.example.regloglist.entity.Change;
import com.example.regloglist.entity.Role;
import com.example.regloglist.entity.UserEntity;
import com.example.regloglist.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserController {
    private final UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/registration")
    public String getRegisterPage(Model model) {
        model.addAttribute("user", new UserEntity());
        return "registration";
    }

    @PostMapping("/registration")
    public String postRegisterPage(@ModelAttribute("user") UserEntity user) {
        if (userRepo.findByUsername(user.getUsername()) != null) {
            return "redirect:/registration";
        }
        user.setUser_role(Collections.singleton(Role.DEFAULT));
        user.setActivity(false);
        userRepo.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login";
    }

    @PostMapping("/login")
    public String getLoginPage(@ModelAttribute("user") UserEntity user) {
        UserEntity inBase = userRepo.findByUsername(user.getUsername());
        if (inBase == null) {
            return "redirect:/login";
        }

        if (!inBase.getPassword().equals(user.getPassword())) {
            return "redirect:/login";
        }
        inBase.setActivity(true);
        userRepo.save(inBase);
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String getIndexPage(Model model) {
        List<UserEntity> users = new ArrayList<>();

        for (UserEntity user : userRepo.findAll()) {
            users.add(user);
        }

        model.addAttribute("users", users);
        model.addAttribute("change", new Change());
        return "index";
    }

    @PostMapping("/index")
    public String changeRole(@ModelAttribute("change") Change change) {
        UserEntity inBase = userRepo.findByUsername(change.getUsername());
        if (inBase == null) {
            return "redirect:/index";
        }

        inBase.setUser_role(Collections.singleton(change.getRole()));
        userRepo.delete(inBase);
        userRepo.save(inBase);
        return "redirect:/index";
    }
}
