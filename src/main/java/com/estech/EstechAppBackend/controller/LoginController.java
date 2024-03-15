package com.estech.EstechAppBackend.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/api/login")
public class LoginController {

    @GetMapping
    public RedirectView requestLogin() {
        return new RedirectView("/login");
    }

}
