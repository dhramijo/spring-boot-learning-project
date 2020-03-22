package co.uk.jdreamer.repository;

import co.uk.jdreamer.model.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserDao {

    List<User> selectAllUsers();

    Optional<User> selectUserByUserUid(UUID userId);

    int insertUser(UUID userUId, User user);

    int updateUser(User user);

    int deleteUserByUserUid(UUID userId);

}
