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

package  com.example;

import app.AudioManager;
import app.User;
import app.Util;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.*;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Map;

import app.DatabaseObject;

//import api.VoiceMetaData;
//import src.main.java.app.AudioManager;

@Controller
@SpringBootApplication
@SessionAttributes("Login")
public class Main {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    //@Autowired
    private DataSource dataSource;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }

    @RequestMapping("/")
    String index(Map<String, Object> model, HttpSession session) {
        Util.checkLoggedIn(model, session);
        return "index";
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


//  @GetMapping("/record")
//  String record() {
//    return "record";
//  }



    @GetMapping("/confirm")
    String confirm(@RequestParam("id") String id) {
        Connection connection = null;
        try {
            connection = DatabaseObject.getConnection();
            Statement stmt = connection.createStatement();
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE valid = ? LIMIT 1;");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String username = rs.getString("username");
                ps = connection.prepareStatement("UPDATE users SET valid = 'true' WHERE username = ?;");
                ps.setString(1, username);
                ps.execute();
            } else {
                return "error";
            }
            return "redirect:/login";
        } catch (Exception e) {
            return "error";
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
            }
        }
    }

    @GetMapping("/record")
    String record(Map<String, Object> model, HttpSession session) {
        Connection connection = null;
        Util.checkLoggedIn(model, session);
        try {
            if(session.getAttribute("Login") == null)
                return "error";
            connection = DatabaseObject.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM phrases ORDER BY RANDOM() LIMIT 1");
            ArrayList<String> output = new ArrayList<String>();
            while (rs.next()) {
                output.add(rs.getString("phrase"));
            }

            model.put("records", output);
            connection.close();
            return "record";
        } catch (Exception e) {
            model.put("message", e.getMessage());
            return "error";
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
            }
        }
    }


    @RequestMapping("/game")
    String game(Map<String, Object> model) {
        Connection connection = null;
        try {
            connection = getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM phrases ORDER BY RANDOM() LIMIT 1");
            ArrayList<String> output = new ArrayList<String>();
            while (rs.next()) {
                output.add(rs.getString("phrase"));
            }

            model.put("records", output);
            connection.close();
            return "game";
        } catch (Exception e) {
            model.put("message", e.getMessage());
            return "error";
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
            }
        }
    }

    // temporary
//  @PostMapping("/audio")
//  String audio(String audio, String prompt, String user) {
//    app.AudioManager am = new app.AudioManager();
//    return am.analyze(audio, prompt, user);
//  }

    @MessageMapping("/audio")
    public String audio(String packet) throws Exception {
        System.out.println("incoming message ...");

        JSONObject obj = new JSONObject(packet);
        String text = obj.getString("text");
        String audio = obj.getString("audio");

//    System.out.println(text + audio);

        // write to file
//    Base64.Decoder decoder = Base64.getDecoder();
//    byte[] decodedByte = decoder.decode(arr.split(",")[1]);
//    FileOutputStream fos = new FileOutputStream("clip.wav");
//    fos.write(decodedByte);
//    fos.close();
        AudioManager am = new AudioManager();
        return am.analyze(audio, text, "default");
    }

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
