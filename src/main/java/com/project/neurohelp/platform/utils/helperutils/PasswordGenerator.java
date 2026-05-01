package com.project.neurohelp.platform.utils.helperutils;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class PasswordGenerator {
    public String generateRandomPassword(int length) {
        Dotenv dotenv = Dotenv.load();
        String randomString = dotenv.get("RANDOM_STRING");
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = (int) (Math.random() * randomString.length());
            password.append(randomString.charAt(index));
        }
        return password.toString();
    }
}
