package isdcm.tomcat.webapp.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "usuario")
public class User {

    @Id
    @SequenceGenerator(name = "userIdGenerator", sequenceName = "usuario_user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userIdGenerator")
    @Column(name = "user_id")
    private Integer userId;

    @Column
    private String name;

    @Column
    private String surnames;

    @Column
    private String email;

    @Column
    private String password;

    @Column(name = "nick_name")
    private String nickName;

}
