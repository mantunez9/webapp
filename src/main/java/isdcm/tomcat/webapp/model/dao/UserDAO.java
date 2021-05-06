package isdcm.tomcat.webapp.model.dao;

import isdcm.tomcat.webapp.model.User;
import isdcm.tomcat.webapp.model.vo.ResultActionsCRUD;
import isdcm.tomcat.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
import java.util.Optional;

@Service
public class UserDAO {

    private final UserRepository userRepository;

    @Autowired
    public UserDAO(UserRepository userRepository) {this.userRepository = userRepository;}

    public Optional<User> findByNickName(String nickName) {

        try {
            return userRepository.findByNickName(nickName);
        } catch (NoResultException exception) {
            return Optional.empty();
        }

    }

    public Optional<User> findByEmail(String email) {

        try {
            return userRepository.findByEmail(email);
        } catch (NoResultException exception) {
            return Optional.empty();
        }

    }

    public ResultActionsCRUD createUser(User user) {

        try {

            userRepository.saveAndFlush(user);

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

}
