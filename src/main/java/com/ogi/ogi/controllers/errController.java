package com.ogi.ogi.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class errController implements ErrorController {

    @RequestMapping("/error")
    @ResponseBody
    @GetMapping
    String error(HttpServletRequest request) {
        return "<h1>404</h1>";
    }


    public String getErrorPath() {
        return "/error";
    }
}