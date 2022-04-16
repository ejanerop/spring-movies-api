package com.janero.movies.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping()
    public RedirectView redirectWithUsingRedirectView(RedirectAttributes attributes) {
        return new RedirectView("/swagger-ui/index.html");
    }

}
