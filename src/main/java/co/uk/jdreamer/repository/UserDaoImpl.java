package co.uk.jdreamer.repository;

import co.uk.jdreamer.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("userDao")
public class UserDaoImpl implements UserDao {

    private static Map<UUID, User> database;

    public UserDaoImpl() {
        database = new HashMap<UUID, User>();
        UUID userId = UUID.randomUUID();
        database.put(
                userId,
                new User(
                        userId,
                        "Jo",
                        "Dreamer",
                        User.GENDER.MALE,
                        22,
                        "jdreamer@gmail.com")
        );
    }

    @Override
    public List<User> selectAllUsers() {
        return new ArrayList<>(database.values());
    }

    @Override
    public Optional<User> selectUserByUserUid(UUID userId) {
        return Optional.ofNullable(database.get(userId)); // Check if userId is null
    }

    @Override
    public void insertUser(User user) {
        database.put(user.getUserUid(),user);
    }

    @Override
    public int updateUser(User user) {
        database.put(user.getUserUid(),user);
        return 1;
    }

    @Override
    public int deleteUserByUserUid(UUID userId) {
        database.remove(userId);
        return 1;
    }
}
