package com.ogi.ogi.controllers;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class js{
    private int id;
    private int size;
    private String text;
}

@Controller
public class Controllers {
    @RequestMapping(value="/try", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public JSONObject jsnp(@RequestBody JSONObject js1){
        System.out.println(js1);
        return js1;
    }
    @GetMapping("/example")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {

        return String.format("Hello %s!", name);
    }
    @GetMapping("/files")
    public Set<String> listFilesUsingJavaIO() {
        return Stream.of(Objects.requireNonNull(new File("files/").listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("title","Main page");
        return "home";
    }
}