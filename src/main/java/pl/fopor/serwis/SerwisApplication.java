package pl.fopor.serwis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;

@SpringBootApplication
public class SerwisApplication {

    public static void main(String[] args) {

        try (BufferedReader br = new BufferedReader(new FileReader(("src/main/resources/application.properties")))) {

            String s;
            while((s = br.readLine()) != null) {
                System.out.println(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        SpringApplication.run(SerwisApplication.class, args);
    }

}
