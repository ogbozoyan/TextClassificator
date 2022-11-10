package com.ogi.ogi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("status")
public class userController {
    @GetMapping
    public ResponseEntity getStatus(){
        try{
            return ResponseEntity.ok("Server works");
        } catch (Exception  e){
            return ResponseEntity.badRequest().body("Error in getUser");
        }
    }
}
