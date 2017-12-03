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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import app.User;
//import src.main.java.app.AudioManager;

@Controller
@SpringBootApplication
public class LoginController {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  //@Autowired
  private DataSource dataSource;

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
  String loginSubmit(@ModelAttribute User user) {
    // Use 'user' variable (which should contain a username and password) to verify a user in the database.
try (Connection connection = dataSource.getConnection()) {
     	Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users"
      +" (user_id SERIAL NOT NULL PRIMARY KEY,"
      +" username varchar(225) NOT NULL UNIQUE,"
      +" password varchar(225),"
      +" user_type varchar(255))");
      ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE username = " + user.getUsername() + " AND password = " + user.getPassword() + "LIMIT 1;");
	if(rs.getFetchSize() == 0)
		return "error";
	else 
		return "index";
    } catch (Exception e) {
      return "error";
    }
  }

}