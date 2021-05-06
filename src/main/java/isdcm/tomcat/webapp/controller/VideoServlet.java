package isdcm.tomcat.webapp.controller;

import com.google.gson.Gson;
import isdcm.tomcat.webapp.model.Video;
import isdcm.tomcat.webapp.model.VideoRestResponse;
import isdcm.tomcat.webapp.model.dao.VideoDAO;
import isdcm.tomcat.webapp.model.vo.ResultActionsCRUD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/video")
public class VideoServlet {

    static final DateTimeFormatter format = DateTimeFormatter.ISO_LOCAL_DATE;
    static final DateTimeFormatter hora = DateTimeFormatter.ISO_LOCAL_TIME;
    public static final String VIDEOS = "videos";
    public static final String ERROR = "error";

    private final VideoDAO videoDAO;

    @Autowired
    public VideoServlet(VideoDAO videoDAO) {this.videoDAO = videoDAO;}

    @GetMapping
    public String doGet(HttpServletRequest req, HttpServletResponse resp) {

        try {

            HttpSession session = req.getSession(false);
            String title = req.getParameter("byTitle");
            String author = req.getParameter("byAuthor");
            String date = req.getParameter("byCreationDate");

            if (session != null && session.getAttribute("user") != null) {

                if (title != null && !title.equals("")) {
                    req.setAttribute(VIDEOS, videoDAO.findByTitle(title));
                } else if (author != null && !author.equals("")) {
                    req.setAttribute(VIDEOS, videoDAO.findByAuthor(author));
                } else if (date != null && !date.equals("")) {
                    req.setAttribute(VIDEOS, videoDAO.findByDate(date));
                } else {
                    req.setAttribute(VIDEOS, videoDAO.findAllVideo());
                }

                return "gestionVid";

            } else {

                return "login";

            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return ERROR;

    }

    @PostMapping
    public String doPost(HttpServletRequest req, HttpServletResponse resp) {

        try {

            String titulo = req.getParameter("titulo");
            String autor = req.getParameter("autor");
            String date = req.getParameter("date");
            String duracion = req.getParameter("duracion");
            String reproducciones = req.getParameter("reproducciones");
            String descripcion = req.getParameter("descripcion");
            String formato = req.getParameter("formato");
            String url = req.getParameter("url");

            Video video = new Video();
            video.setAuthor(autor);
            video.setDescription(descripcion);
            video.setCreationDate(LocalDate.parse(date, format).atStartOfDay());
            video.setDuration(LocalTime.parse(duracion, hora));
            video.setReproduction(Integer.parseInt(reproducciones));
            video.setFormat(formato);
            video.setTittle(titulo);
            video.setUrl(url);

            ResultActionsCRUD resultActionsCRUD = videoDAO.createVideo(video);

            if (resultActionsCRUD.isOk()) {

                req.setAttribute(VIDEOS, videoDAO.findAllVideo());
                return "gestionVid";

            } else {

                //ERROR !
                req.setAttribute(ERROR, resultActionsCRUD.getMissatge());
                return ERROR;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ERROR;

    }

    @PutMapping
    public void doPut(HttpServletRequest req, HttpServletResponse resp) {

        try {

            VideoRestResponse response = videoDAO.updateReproductions(Integer.valueOf(req.getParameter("id")));
            req.setAttribute(VIDEOS, videoDAO.findAllVideo());

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(new Gson().toJson(response));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
