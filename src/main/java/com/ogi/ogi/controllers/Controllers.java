package com.ogi.ogi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.URI;
import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class Controllers {
    long id_t;
    @RequestMapping(value="/save", method = RequestMethod.POST, consumes = "application/json")
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
    @GetMapping("/classify/file")
    public String classifyFile(@RequestParam(value = "file", defaultValue = "test.txt") String name) throws IOException, InterruptedException {
        try {
            fileController desk = new fileController(name);
            HashMap<String, String> jsn1 = desk.createJson();
            var objectMapper = new ObjectMapper();
            String requestBody = objectMapper
                    .writeValueAsString(jsn1);
            StringEntity params = new StringEntity(requestBody);
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://127.0.0.1:5000/");
            request.addHeader("content-type","application/json");
            request.setEntity(params);
            HttpResponse response = client.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            System.out.println(json);
            return String.format(json);
        }catch (Exception e){
            return String.format("Error %s", e);
        }
    }
    @GetMapping("/classify/text")
    public String classifyText(@RequestParam(value = "text", defaultValue = " ") String text) throws IOException, InterruptedException {
        try {
            this.id_t = Counter.Id;
            HashMap<String, String> jsn1 = new HashMap<>();
            jsn1.put("text",text);
            jsn1.put("size", String.valueOf(text.length()));
            jsn1.put("id", String.valueOf(id_t));
            Counter.Id++;
            var objectMapper = new ObjectMapper();
            String requestBody = objectMapper
                    .writeValueAsString(jsn1);
            StringEntity params = new StringEntity(requestBody);
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://127.0.0.1:5000/");
            request.addHeader("content-type","application/json");
            request.setEntity(params);
            HttpResponse response = client.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            System.out.println(json);
            return String.format(json);
        }catch (Exception e){
            return String.format("Error %s", e);
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