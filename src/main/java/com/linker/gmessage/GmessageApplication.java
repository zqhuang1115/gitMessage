package com.linker.gmessage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class GmessageApplication {

    String name(){
        return "hello";
    }
    public static void main(String[] args) {
        SpringApplication.run(GmessageApplication.class, args);
    }

}
