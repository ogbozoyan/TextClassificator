//https://www.tutorialspoint.com/tika/tika_content_extraction.htm
package com.ogi.ogi;
import java.io.IOException;
/*
curl -i \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-X POST --data \
  '{"id": 1,"size": 10,"text": "Jopa"}' "http://localhost:8080/try"
 */

import org.apache.tika.exception.TikaException;
import org.json.JSONObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import com.ogi.ogi.controllers.fileController;

@SpringBootApplication
@RestController

public class OgiApplication {
	public static void main(String[] args) throws IOException, TikaException {

		SpringApplication.run(OgiApplication.class, args);
	}
}
