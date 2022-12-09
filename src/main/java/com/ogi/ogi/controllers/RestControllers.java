package com.ogi.ogi.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ogi.ogi.models.Data;
import com.ogi.ogi.models.RequestForm;
import com.ogi.ogi.repo.DataRepository;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.bouncycastle.x509.NoSuchStoreException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class RestControllers {
    private final static String FOLDER = "files/";
    @Autowired
    private DataRepository dataRepository;
    @CrossOrigin
    @PostMapping("/upload")
    public ResponseEntity<HashMap> request(@RequestBody RequestForm requestForm) throws IOException {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("content-type", "application/json");
        if(!requestForm.getKey()){
            return ResponseEntity.badRequest().headers(responseHeaders).body(new HashMap<String, String>() {{
                put("error", "Authentication failed");
            }});
        }
        if(requestForm.getType() == null || requestForm.getPayload() == null){
            return ResponseEntity.badRequest().headers(responseHeaders).body(new HashMap<String, String>() {{
                put("error", "Invalid request");
            }});
        }
        else if (requestForm.getType().equals("file")){
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://127.0.0.1:8080/upload/file");
            String body = requestForm.getPayload();
            HttpEntity entityPayload = new ByteArrayEntity(body.getBytes("UTF-8"));
            request.addHeader("content-type", "text/plain");
            request.setEntity(entityPayload);
            HttpResponse response = client.execute(request);
            String result_from_classifier = EntityUtils.toString(response.getEntity())
                    .replace("\n", "")
                    .replace("\r", "")
                    .replace("\t", "")
                    .replace(" ", "");
            HashMap result = new ObjectMapper().readValue(result_from_classifier, HashMap.class);
            return ResponseEntity.ok().headers(responseHeaders).body(result);
        }
        else if (requestForm.getType().equals("text")){
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://127.0.0.1:8080/upload/text");
            String body = requestForm.getPayload();
            HttpEntity entityPayload = new ByteArrayEntity(body.getBytes(StandardCharsets.UTF_8));
            request.addHeader("content-type", "text/plain");
            request.setEntity(entityPayload);

            HttpResponse response = client.execute(request);

            String result_from_classifier = EntityUtils.toString(response.getEntity())
                    .replace("\n", "")
                    .replace("\r", "")
                    .replace("\t", "")
                    .replace(" ", "");
            HashMap result = new ObjectMapper().readValue(result_from_classifier, HashMap.class);

            return ResponseEntity.ok().headers(responseHeaders).body(result);
        }
        return ResponseEntity.badRequest().headers(responseHeaders).body(new HashMap<String, String>() {{
            put("error", "Invalid request");
        }});
    }
    @PostMapping("/upload/text")
    public ResponseEntity<String> uploadText(@RequestBody String text) throws IOException {
        HashMap<String,String> jsn_ans = new HashMap<>();
        HttpHeaders responseHeaders = new HttpHeaders();
        System.out.println(text);
        if(text == null || text.isEmpty()) {
            jsn_ans.put("status", "Error");
            jsn_ans.put("message", "text is empty");
            responseHeaders.set("content-type", "application/json");
            return ResponseEntity
                    .badRequest()
                    .headers(responseHeaders)
                    .body(jsn_ans.get("status") + " " + jsn_ans.get("message"));
        } else {

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
            jsn_ans.put("result",result_from_classifier);
            responseHeaders.set("content-type", "application/json");
            return ResponseEntity.ok()
                    .body(jsn_ans.get("result"));
        }
    }
    @PostMapping("/upload/file")
    public ResponseEntity<HashMap> singleFileUpload(@RequestBody MultipartFile file) {
        HashMap<String,String> jsn_ans = new HashMap<>();
        HttpHeaders responseHeaders = new HttpHeaders();
        if (file.isEmpty()) {
            jsn_ans.put("status", "Error");
            jsn_ans.put("message", "Please select a file to upload");
            responseHeaders.set("content-type", "application/json");
            return ResponseEntity
                    .badRequest()
                    .headers(responseHeaders)
                    .body(jsn_ans);
        } else {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get(FOLDER + file.getOriginalFilename());
                Files.write(path, bytes);

                jsn_ans.put("status", "Good");
                jsn_ans.put("message", "Uploaded");

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
                jsn_ans.put("result",result_from_classifier);
                if (result_from_classifier.isEmpty()) {
                    jsn_ans.put("status", "Error");
                    jsn_ans.put("message", "|Classifier internal error|");
                }
                responseHeaders.set("content-type", "application/json");
                return ResponseEntity.ok().headers(responseHeaders).body(jsn_ans);
            } catch (IOException e) {
                jsn_ans.put("status", "Error");
                jsn_ans.put("message", String.valueOf(e));
                return ResponseEntity
                        .badRequest()
                        .headers(responseHeaders)
                        .body(jsn_ans);
            }
        }
    }
    @GetMapping("/data")
    public Iterable<Data> data(){
        return dataRepository.findAll();
    }
    @GetMapping("/files")
    public Set<String> listFilesUsingJavaIO() {
        return Stream.of(Objects.requireNonNull(new File("files/").listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }
    @GetMapping("/classify/text")
    public String classifyText(@RequestParam(value = "text", defaultValue = "") String text) throws IOException, InterruptedException {
        try {
            Data n = new Data();
            HashMap<String, String> jsn = new HashMap<>();
            jsn.put("text",text);
            jsn.put("size", String.valueOf(text.length()));
            n.setText(jsn.get("text"));
            n.setSize(jsn.get("size"));
            dataRepository.save(n);
            jsn.put("id", String.valueOf(n.getId()));
            var objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(jsn);
            StringEntity params = new StringEntity(requestBody);
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost request = new HttpPost("http://127.0.0.1:5000/");
            request.addHeader("content-type","application/json");
            request.setEntity(params);
            HttpResponse response = client.execute(request);
            String json = EntityUtils.toString(response.getEntity());
            System.out.println(json);
            if(json.isEmpty()){
                System.out.println("Включи классификатор дубина!!!");
                return "Can't reach classifier";
            }
            return String.format(json);
        }catch (Exception e){
            return String.format("Error %s", e);
        }
    }
}