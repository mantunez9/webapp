package isdcm.tomcat.webapp.repository;

import isdcm.tomcat.webapp.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {

    List<Video> findAllVideoByTittle(@Param("tittle") String tittle);

    List<Video> findAllVideoByAuthor(@Param("author") String author);

    List<Video> findAllVideoByCreationDateGreaterThanEqual(@Param("creationDate") LocalDateTime creationDate);

}
