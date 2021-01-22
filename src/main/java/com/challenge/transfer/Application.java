package com.challenge.transfer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Application class.
 */
//CHECKSTYLE.OFF: HideUtilityClassConstructor - This is not Utility class
@SpringBootApplication
public class Application {

    /**
     * Main system method.
     *
     * @param args System arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
//CHECKSTYLE.ON: HideUtilityClassConstructor - Simple dictionary
