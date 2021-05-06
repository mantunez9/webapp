package isdcm.tomcat.webapp.controller;

import isdcm.tomcat.webapp.model.User;
import isdcm.tomcat.webapp.model.dao.UserDAO;
import isdcm.tomcat.webapp.model.dao.VideoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/login")
public class UserLoginServlet {

    private final UserDAO userDAO;
    private final VideoDAO videoDAO;

    @Autowired
    public UserLoginServlet(UserDAO userDAO, VideoDAO videoDAO) {
        this.userDAO = userDAO;
        this.videoDAO = videoDAO;
    }

    @GetMapping
    public String doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {

            HttpSession session = req.getSession(false);

            if (session != null && session.getAttribute("user") != null) {

                req.setAttribute("videos", videoDAO.findAllVideo());
                return "gestionVid";

            }

            return "login";

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "error";

    }

    @PostMapping
    public String doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {

            String nickName = req.getParameter("nickName");
            String pass = req.getParameter("password");

            Optional<User> userOptional = userDAO.findByNickName(nickName);

            if (userOptional.isPresent() && userOptional.get().getPassword().equals(pass)) {

                HttpSession session = req.getSession();
                session.setAttribute("user", userOptional.get());
                req.setAttribute("videos", videoDAO.findAllVideo());
                return "gestionVid";

            } else {

                String message = "Incorrect username or password";
                req.setAttribute("message", message);
                return "login";

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return "error";

    }

}
