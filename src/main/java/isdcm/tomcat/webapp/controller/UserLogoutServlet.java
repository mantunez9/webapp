package isdcm.tomcat.webapp.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "userLogoutServlet", value = "/logout")
public class UserLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {

            HttpSession session = req.getSession(false);

            if (session != null) {
                session.invalidate();
                req.getRequestDispatcher("/login").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
