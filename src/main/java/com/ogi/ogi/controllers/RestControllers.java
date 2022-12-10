package com.ogi.ogi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogi.ogi.models.Data;
import com.ogi.ogi.models.RequestForm;
import com.ogi.ogi.repo.DataRepository;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;


@RestController
public class RestControllers {
    private String s_token = "token";

    private final static String FOLDER = "files/";
    @Autowired
    private DataRepository dataRepository;
    @CrossOrigin
    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody RequestForm token) throws IOException, ParseException {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");
        String s_token = "token";
        if(token.getToken().equals(s_token)){
            return ResponseEntity.ok().headers(responseHeaders).body("{\"result\":true}");
        }
        else{
            return ResponseEntity.ok().headers(responseHeaders).body("{\"result\":false}");
        }
    }
    @CrossOrigin
    @PostMapping("{c_token}/upload/text")
    public ResponseEntity<String> uploadText(@RequestBody String text,@PathVariable String c_token) throws IOException {
        if(c_token.equals(this.s_token)) {
            HashMap<String, String> jsn_ans = new HashMap<>();
            HttpHeaders responseHeaders = new HttpHeaders();
            System.out.println(text);
            if (text == null || text.isEmpty()) {
                jsn_ans.put("id", "Error");
                jsn_ans.put("result", "text is empty");
                responseHeaders.set("content-type", "application/json");
                return ResponseEntity
                        .badRequest()
                        .headers(responseHeaders)
                        .body(jsn_ans.get("result") + " " + jsn_ans.get("id"));
            } else {
                responseHeaders.set("content-type", "application/json");
                Data n = new Data();

                HashMap<String, String> jsnfile = new HashMap<>();
                jsnfile.put("text", text);
                jsnfile.put("size", String.valueOf(text.length()));
                n.setText(jsnfile.get("text"));
                n.setSize(jsnfile.get("size"));
                n.setFname("TEXT");
                dataRepository.save(n);
                jsnfile.put("id", String.valueOf(n.getId()));
                var objectMapper = new ObjectMapper();
                String requestBody = objectMapper.writeValueAsString(jsnfile);
                StringEntity params = new StringEntity(requestBody);
                HttpClient client = HttpClientBuilder.create().build();
                HttpPost request = new HttpPost("http://127.0.0.1:5000/");
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                HttpResponse response = client.execute(request);
                String result_from_classifier = EntityUtils.toString(response.getEntity());
                System.out.println(result_from_classifier);
                jsn_ans.put("result", result_from_classifier);

                return ResponseEntity.ok()
                        .body(jsn_ans.get("result"));
            }
        }
        return ResponseEntity.badRequest()
                .body("{\"result\":\"Auth error\",\"id\":\"Error\"}");
    }
    @CrossOrigin
    @PostMapping("{c_token}/upload/file")
    public ResponseEntity<String> singleFileUpload(@RequestBody MultipartFile file,@PathVariable String c_token) {
        if(c_token.equals(this.s_token)) {
            HashMap<String, String> jsn_ans = new HashMap<>();
            HttpHeaders responseHeaders = new HttpHeaders();
            if (file.isEmpty()) {
                jsn_ans.put("id", "Error");
                jsn_ans.put("result", "text is empty");
                responseHeaders.set("content-type", "application/json");
                return ResponseEntity
                        .badRequest()
                        .headers(responseHeaders)
                        .body(jsn_ans.get("id") + " " + jsn_ans.get("result"));
            } else {
                try {
                    responseHeaders.set("content-type", "application/json");

                    byte[] bytes = file.getBytes();
                    Path path = Paths.get(FOLDER + file.getOriginalFilename());
                    Files.write(path, bytes);

                    Data n = new Data();
                    fileController desk = new fileController(file.getOriginalFilename());
                    HashMap<String, String> jsnfile = desk.createJson();
                    n.setText(jsnfile.get("text"));
                    n.setSize(jsnfile.get("size"));
                    n.setFname(file.getOriginalFilename());
                    dataRepository.save(n);
                    jsnfile.put("id", String.valueOf(n.getId()));
                    var objectMapper = new ObjectMapper();
                    String requestBody = objectMapper.writeValueAsString(jsnfile);
                    StringEntity params = new StringEntity(requestBody);
                    HttpClient client = HttpClientBuilder.create().build();
                    HttpPost request = new HttpPost("http://127.0.0.1:5000/");
                    request.addHeader("content-type", "application/json");
                    request.setEntity(params);
                    HttpResponse response = client.execute(request);
                    String result_from_classifier = EntityUtils.toString(response.getEntity());
                    System.out.println(result_from_classifier);
                    jsn_ans.put("result", result_from_classifier);

                    if (result_from_classifier.isEmpty()) {
                        jsn_ans.put("id", "Error");
                        jsn_ans.put("result", "|Classifier internal error|");
                        return ResponseEntity
                                .badRequest()
                                .headers(responseHeaders)
                                .body(jsn_ans.get("id") + " " + jsn_ans.get("result"));
                    }

                    return ResponseEntity
                            .ok()
                            .headers(responseHeaders)
                            .body(jsn_ans.get("result"));

                } catch (IOException e) {
                    jsn_ans.put("id", "Error");
                    jsn_ans.put("result", String.valueOf(e));
                    return ResponseEntity
                            .badRequest()
                            .headers(responseHeaders)
                            .body(jsn_ans.get("id") + " " + jsn_ans.get("result"));
                }
            }
        }
        return ResponseEntity.badRequest()
                .body("{\"result\":\"Auth error\",\"id\":\"Error\"}");
    }
    @GetMapping("/data")
    public Iterable<Data> data(){
        return dataRepository.findAll();
    }
}