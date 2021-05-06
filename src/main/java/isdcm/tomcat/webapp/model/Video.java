package isdcm.tomcat.webapp.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Entity
@Table(name = "video")
public class Video {

    @Id
    @SequenceGenerator(name = "videoIdGenerator", sequenceName = "video_video_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "videoIdGenerator")
    @Column(name = "video_id")
    private Integer videoId;

    @Column
    private String tittle;

    @Column
    private String author;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column
    private LocalTime duration;

    @Column
    private int reproduction;

    @Column
    private String description;

    @Column
    private String format;

    @Column
    private String url;

}
