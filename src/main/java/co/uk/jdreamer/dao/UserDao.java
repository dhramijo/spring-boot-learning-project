package co.uk.jdreamer.dao;

import co.uk.jdreamer.model.User;

import java.util.List;
import java.util.UUID;

public interface UserDao {

    List<User> getAllUsers();

    User getUser(UUID userId);

    int insertUser(User user);

    int updateUser(User user);

    int deleteUser(UUID userId);

}
