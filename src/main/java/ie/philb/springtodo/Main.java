/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package ie.philb.springtodo;

import ie.philb.springtodo.controller.WebUiController;
import java.sql.SQLException;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;

/**
 *
 * @author Philip.Bradley
 */
@SpringBootApplication
@ImportResource("classpath:beans.xml")
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Main.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
    }

    // Allow remote access to DB using URL jdbc:h2:tcp://<ipaddress>:9092/mem:<dbname>
    /*
    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() {

        Server server = null;
        try {
            logger.info("Starting H2 server...");
            server = Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
            logger.info("H2 server started");
        } catch (SQLException ex) {
            logger.error("Failed to start H2 server", ex);
        }
        
        return server;
    }
     */
}
