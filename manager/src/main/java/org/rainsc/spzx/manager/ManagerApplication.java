package org.rainsc.spzx.manager;

import org.rainsc.spzx.manager.properties.UserProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(value = {UserProperties.class})
@ComponentScan(basePackages = {"org.rainsc.spzx"})
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class);
        System.out.println("http://127.0.0.1:8501/doc.html");
    }
}
