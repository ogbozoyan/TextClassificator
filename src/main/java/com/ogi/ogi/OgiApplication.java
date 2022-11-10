//https://www.tutorialspoint.com/tika/tika_content_extraction.htm
package com.ogi.ogi;
import java.io.IOException;


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
		fileController desk = new fileController("test.txt");
		JSONObject jsn = desk.createJson();
		System.out.println(jsn);
		SpringApplication.run(OgiApplication.class, args);
	}
}
