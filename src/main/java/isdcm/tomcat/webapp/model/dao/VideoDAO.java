package isdcm.tomcat.webapp.model.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import isdcm.tomcat.webapp.model.Video;
import isdcm.tomcat.webapp.model.VideoRestResponse;
import isdcm.tomcat.webapp.model.vo.ResultActionsCRUD;
import isdcm.tomcat.webapp.repository.VideoRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VideoDAO {

    private final VideoRepository videoRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public VideoDAO(VideoRepository videoRepository) {this.videoRepository = videoRepository;}

    public ResultActionsCRUD createVideo(Video video) {

        try {

            videoRepository.saveAndFlush(video);

            return ResultActionsCRUD
                    .builder()
                    .isOk(true)
                    .build();

        } catch (Exception e) {

            return ResultActionsCRUD
                    .builder()
                    .missatge(e.getMessage())
                    .isOk(false)
                    .build();

        }

    }

    public VideoRestResponse updateReproductions(Integer id) throws IOException {

        URL url = new URL("http://localhost:9000/video/" + id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoOutput(true);
        connection.setInstanceFollowRedirects(false);
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/xml");

        if (connection.getResponseCode() != 200) {
            throw new RuntimeException("Failed : HTTP Error code : "
                    + connection.getResponseCode());
        }

        VideoRestResponse restResponse = objectMapper.readValue(connection.getInputStream(), VideoRestResponse.class);
        connection.disconnect();
        return restResponse;

    }

    public List<Video> findAllVideo() {
        return videoRepository.findAll();
    }

    public List<Video> findByDate(String date) {
        String[] splited = date.split("-");
        LocalDateTime dateTime = LocalDateTime.of(Integer.parseInt(splited[0]), Integer.parseInt((StringUtils.isNotBlank(splited[1]) ? splited[1] : "01")), Integer.parseInt((StringUtils.isNotBlank(splited[2]) ? splited[2] : "01")), 0, 0);
        return videoRepository.findAllVideoByCreationDateGreaterThanEqual(dateTime);
    }

    public List<Video> findByTitle(String title) {
        return videoRepository.findAllVideoByTittle(title);
    }

    public List<Video> findByAuthor(String author) {
        return videoRepository.findAllVideoByAuthor(author);
    }

}
