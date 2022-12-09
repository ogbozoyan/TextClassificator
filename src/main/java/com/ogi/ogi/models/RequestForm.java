package com.ogi.ogi.models;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.springframework.web.multipart.MultipartFile;

public class RequestForm {
    private String token;

    public String getToken() {
        return token;
    }
}
