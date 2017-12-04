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

import app.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

//import api.VoiceMetaData;
//import src.main.java.app.AudioManager;

@Controller
@SpringBootApplication
public class Main {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  //@Autowired
  private DataSource dataSource;

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Main.class, args);
  }

  @RequestMapping("/")
  String index() {
    return "index";
  }

/*  @GetMapping("/login")
  String loginForm() {
    return "login";
  }

  // login endpoint and committing change
  @PostMapping("/login")
  String loginSubmit(@ModelAttribute User user) {
    // Use 'user' variable (which should contain a username and password) to verify a user in the database.
    // Hash the password, and sanitize inputs
    return "login";
  }*/

  // signup endpoint and committing change
  @GetMapping("/sign-up")
  String signupForm() {
    return "signup";
  }

  @PostMapping("/sign-up")
  String signupSubmit(@ModelAttribute User user) {
    // Use 'user' variable (username/password/type) to create a new row in the user database table.
    // Hash the password, and sanitize inputs
    return "signup";
  }
   // password endpoint and committing change
  @GetMapping("/password")
  String passwordForm() {
    return "password";
  }

  @PostMapping("/password")
  String passwordSubmit(@ModelAttribute User user) {

    return "password";
  }

/*
  @GetMapping("/record")
  String record() {
    return "record";
  }
*/

@GetMapping("/record")
String record(Map<String, Object> model) {
  try (Connection connection = getConnection()) {
    Statement stmt = connection.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM phrases ORDER BY RANDOM() LIMIT 1");
    ArrayList<String> output = new ArrayList<String>();
    while (rs.next()) {
      output.add(rs.getString("phrase"));
    }

    model.put("records", output);
    return "record";
  } catch (Exception e) {
    model.put("message", e.getMessage());
    return "error";
  }
}


  @RequestMapping("/db")
    String db(Map<String, Object> model) {
      try (Connection connection = getConnection()) {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
        stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
        ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

        ArrayList<String> output = new ArrayList<String>();
        while (rs.next()) {
          output.add("Read from DB: " + rs.getTimestamp("tick"));
        }

        model.put("records", output);
        return "db";
      } catch (Exception e) {
        model.put("message", e.getMessage());
        return "error";
      }
    }

  @RequestMapping("/users")
  String users(Map<String, Object> model) {
    try (Connection connection = getConnection()) {
      Statement stmt = connection.createStatement();
      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users"
      +" (user_id SERIAL NOT NULL PRIMARY KEY,"
      +" username varchar(225) NOT NULL UNIQUE,"
      +" password varchar(225),"
      +" user_type varchar(255))");
      ResultSet rs = stmt.executeQuery("SELECT * FROM users");
      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add("Read from DB: " + rs.next());
      }

      model.put("records", output);
      return "db";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  @RequestMapping("/game")
  String game(Map<String, Object> model) {
    try (Connection connection = getConnection()) {
      Statement stmt = connection.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM phrases ORDER BY RANDOM() LIMIT 1");
      ArrayList<String> output = new ArrayList<String>();
      while (rs.next()) {
        output.add(rs.getString("phrase"));
      }

      model.put("records", output);
      return "game";
    } catch (Exception e) {
      model.put("message", e.getMessage());
      return "error";
    }
  }

  // temporary
  @PostMapping("/audio")
  String audio(String audio) {
    return "success";
  }

  @MessageMapping ("/audio")
  public void stream(String base64Audio) throws Exception {
    System.out.println("incoming message ...");
    // write to file
//    Base64.Decoder decoder = Base64.getDecoder();
//    byte[] decodedByte = decoder.decode(base64Audio.split(",")[1]);
//    FileOutputStream fos = new FileOutputStream("clip.wav");
//    fos.write(decodedByte);
//    fos.close();
  }

/*
  @PostMapping("/audio")
  String audio(String audio) {
	  AudioManager am = new AudioManager();
	  return am.analyze(audio);
  }
*/
private static Connection getConnection() throws SQLException {
    String dbUrl = System.getenv("JDBC_DATABASE_URL");
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
