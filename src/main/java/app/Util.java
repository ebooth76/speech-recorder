package app;


import javax.servlet.http.HttpSession;
import java.util.Map;

public class Util {

    public static void checkLoggedIn(Map<String, Object> model, HttpSession session) {
        if (session.getAttribute("Login") == null)
            model.put("loggedIn", "false");
        else
            model.put("loggedIn", "true");
    }
}
