package isdcm.tomcat.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/securityContent")
public class SecurityContentServelet {

    @GetMapping
    public String doGet(HttpServletRequest req) {
        try {
            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("user") != null)
                return "securityContent";
            return "login";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "error";
    }

}
