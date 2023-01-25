/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package ie.philb.springtodo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author Philip.Bradley
 */
@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class Main {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Main.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }
}
