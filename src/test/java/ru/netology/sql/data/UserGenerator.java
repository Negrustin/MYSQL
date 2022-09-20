package ru.netology.sql.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Locale;

public class UserGenerator {
    private UserGenerator() {

    }


    public static UserInfo getUserInfo() {
        Faker faker = new Faker();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return new UserInfo(faker.internet().uuid(), "user", encoder.encode("password"), "active");
    }


        @Value
        public static class UserInfo {
            private String id;
            private String login;
            private String password;
            private String status;
        }


    }


