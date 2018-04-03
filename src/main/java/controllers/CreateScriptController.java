
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
import app.DatabaseObject;
import app.Phrase;
import app.Util;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.sql.*;
import java.util.Map;

@Controller
@SpringBootApplication
public class CreateScriptController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    // Return the "create_script.html" to the user
    @GetMapping("/create-script")
    String createScriptForm(Map<String, Object> model, HttpSession session) {
        Util.checkLoggedIn(model, session);
        return "create-script";
    }

    // Allow the user to post their own script to the db
    @PostMapping("/create-script")
    String postScript(@ModelAttribute Phrase phrase, Map<String, Object> model, HttpSession session) {
        Util.checkLoggedIn(model, session);
        try {
            Connection connection = DatabaseObject.getConnection();
            PreparedStatement ps = connection.prepareStatement("INSERT INTO phrases (phrase, phrase_name, user_id) VALUES (?,?,?)");
            ps.setString(1, phrase.getPhrase());
            ps.setString(2, phrase.getPhrasename());

            // Get the ID of the user who is logged in and put them in the database

            ps.setLong(3, ((User) session.getAttribute("User")).getId());

            // Run the statement against the database
            ps.execute();
        } catch (Exception e) {
            return "error";
        }
        return "create-script";
    }

}

