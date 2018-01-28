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

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

//import api.VoiceMetaData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

import app.User;
import org.springframework.web.servlet.ModelAndView;
//import src.main.java.app.AudioManager;

@Controller
@SpringBootApplication
@SessionAttributes("Login")
public class LoginController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    //@Autowired
    private DataSource dataSource;

    private User user;

    //public static void main(String[] args) throws Exception {
    //  SpringApplication.run(Main.class, args);
    //}

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // login endpoint and committing change
    @PostMapping("/login")
    String loginSubmit(@ModelAttribute User user, HttpSession session) {
        // Use 'user' variable (which should contain a username and password) to verify a user in the database.

        try (Connection connection = getConnection()) {
            Statement stmt = connection.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users"
                    +" (user_id SERIAL NOT NULL PRIMARY KEY,"
                    +" username varchar(225) NOT NULL UNIQUE,"
                    +" password varchar(225),"
                    +" user_type varchar(255))");
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ? LIMIT 1;");
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();

            if(!rs.isBeforeFirst()) {
                return "error";
            }
            else {
                session.setAttribute("Login", user.getUsername());
                return "record";
            }
        } catch (Exception e) {
            return "error";
        }
    }

    private static Connection getConnection() throws SQLException {
        //String dbUrl = System.getenv("JDBC_DATABASE_URL");
        String dbUrl = "jdbc:postgresql://ec2-54-225-112-61.compute-1.amazonaws.com:5432/ds0m2g3nacuvc?sslmode=require&user=huzjrznhoilodv&password=e3670ef2720dba4d5b47c5298a34e481fc0765298256e29faf0a6237d4726983";
        return DriverManager.getConnection(dbUrl);
    }

    @Bean
    public DataSource dataSource() throws SQLException {
        if (dbUrl == null || dbUrl.isEmpty()) {
            return new HikariDataSource();
        } else {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbUrl);
            return new HikariDataSource(config);
        }
    }
}
