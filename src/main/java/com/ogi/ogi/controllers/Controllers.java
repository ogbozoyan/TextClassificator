package com.ogi.ogi.controllers;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
/*
sent:
{
    "id":int,
    "size":int,
    "text":str
}
get:
{
    "id":int,
    "result":str
}
 */

@Controller
public class Controllers {
    @RequestMapping(value="/try", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public void JSDataConsumer(@RequestBody String JSString) throws ParseException {
        JSONParser parser = new JSONParser();
        try {
            Object jsp = parser.parse(JSString);
            JSONObject js = (JSONObject) jsp;
            System.out.println(js);
            String filename = "files/filtered/"+"ID_"+String.valueOf((js.get("id")));
            FileWriter file = new FileWriter(filename);
            file.write(js.get("result").toString());
            file.close();
        }catch (Exception e){
            System.out.printf("%s %s \n",e,new Throwable().getStackTrace()[1].getLineNumber());
        }
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