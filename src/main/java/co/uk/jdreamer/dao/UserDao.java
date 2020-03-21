package co.uk.jdreamer.dao;

import co.uk.jdreamer.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    List<User> selectAllUsers();

    Optional<User> selectUserByUserUid(UUID userId);

    void insertUser(UUID uuid, User user);

    int updateUser(User user);

    int deleteUserByUserUid(UUID userId);

}
