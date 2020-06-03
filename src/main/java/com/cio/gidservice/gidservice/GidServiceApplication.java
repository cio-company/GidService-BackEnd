package com.cio.gidservice.gidservice;

import com.cio.gidservice.gidservice.utils.PublicIDSeparator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GidServiceApplication {

    public static void main(String[] args) {
        PublicIDSeparator idSeparater = new PublicIDSeparator("https://res.cloudinary.com/demo/image/upload/sample.jpg", "\\S+.jpg$");
        System.out.println(idSeparater.separate());
        SpringApplication.run(GidServiceApplication.class, args);
    }
}
