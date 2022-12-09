package com.ogi.ogi.models;

import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;
import org.springframework.web.multipart.MultipartFile;

public class RequestForm {
    private String key;
    private String type;
    private String  payload;
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public boolean getKey() {
        String token = "token";
        if (this.key.equals(token)) {
            return true;
        }
        return false;

    }

    public String getType() {
        return type;
    }

    public String getPayload() {
        return payload;
    }
}
