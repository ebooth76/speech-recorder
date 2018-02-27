
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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SpringBootApplication
public class LogoutController {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @RequestMapping("/logout")
    public String logout(HttpSession session, SessionStatus status) {
        if(session.getAttribute("Login") == null)
            return "redirect:/";
        else {
            session.setAttribute("Login", "watch ur back");
            session.invalidate();
            return "redirect:/logout";
        }
    }

}