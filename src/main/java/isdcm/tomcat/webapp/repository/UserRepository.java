package isdcm.tomcat.webapp.repository;

import isdcm.tomcat.webapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByNickName(@Param("nickname") String nickName);

    Optional<User> findByEmail(@Param("email") String email);

}
