
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
import app.Mail;
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
import java.security.*;
import java.util.UUID;
import java.math.BigInteger;

@Controller
@SpringBootApplication
public class SignupController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    // signup endpoint and committing change
    @GetMapping("/signup")
    String signupForm(HttpSession session) {
        if(session.getAttribute("Login") != null)
            return "redirect:/";
        else {
            return "signup";
        }
    }

    @PostMapping("/signup")
    String signupSubmit(@ModelAttribute User user) {
        // Use 'user' variable (username/password/type) to create a new row in the user database table.
        // Hash the password, and sanitize inputs
        Connection connection = null;
        try {
            connection = DatabaseObject.getConnection();
            Statement stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ? LIMIT 1;");
            ps.setString(1, user.getUsername());
            ResultSet rs = ps.executeQuery();

            if(rs.isBeforeFirst()) {
                connection.close();
                return "error";
            }
            else {
                // If no username was returned create a new user
                ps = connection.prepareStatement("INSERT INTO users (username, password, user_type, email, firstname, lastname, gender, age, valid) VALUES (?,?,?,?,?,?,?,?,?)");
                ps.setString(1, user.getUsername());
                // Hash the password using MD5
                String password = user.getPassword();
                byte[] bytesOfMessage = password.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] md5Hash = md.digest(bytesOfMessage);
                BigInteger bigInt = new BigInteger(1, md5Hash);
                String hashtext = bigInt.toString(16);
                ps.setString(2, hashtext);
                ps.setString(3, user.getUsertype());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getFirstname());
                ps.setString(6, user.getLastname());
                ps.setString(7, user.getGender());
                ps.setInt(8, user.getAge());
                // Send an email to the specified address for verification
                String emailKey = UUID.randomUUID().toString();
                Mail.SendEmail(user.getEmail(), emailKey);
                ps.setString(9, emailKey);
                ps.execute();
                connection.close();
                return "login";
            }
        } catch (Exception e) {
            return "error";
        }finally{
            try{
                if(connection != null){
                    connection.close();
                }
            }catch(Exception e){
            }
        }
    }
}

