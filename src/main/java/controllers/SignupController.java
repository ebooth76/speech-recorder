
/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example;

import app.DatabaseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import app.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Controller
@SpringBootApplication
public class SignupController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    // signup endpoint and committing change
    @GetMapping("/signup")
    String signupForm(HttpSession session) {
        if(session.getAttribute("Login") != null)
            return "error";
        else {
            return "signup";
        }
    }

    @PostMapping("/signup")
    String signupSubmit(@ModelAttribute User user) {
        // Use 'user' variable (username/password/type) to create a new row in the user database table.
        // Hash the password, and sanitize inputs

        try (Connection connection = DatabaseObject.getConnection()) {
            Statement stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ? LIMIT 1;");
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            if(rs.isBeforeFirst()) {
                return "error";
            }
            else {
                // If no username was returned create a new user
                ps = connection.prepareStatement("INSERT INTO users (username, password, user_type) VALUES (?, ?, ?)");
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getUserType());
                ps.execute();
                return "login";
            }
        } catch (Exception e) {
            return "error";
        }
    }
}

