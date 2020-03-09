package co.uk.jdreamer.dao;

import co.uk.jdreamer.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository("fakeUserDao")
public class FakeUserDaoImpl implements UserDao {

    private static Map<UUID, User> database;

    static {
        database = new HashMap<UUID, User>();
        UUID userId = UUID.randomUUID();
        database.put(
                userId,
                new User(userId,"Jo", "Dreamer", User.GENDER.MALE,
                        22,"jdreamer@gmail.com")
        );
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUser(UUID userId) {
        return null;
    }

    @Override
    public int insertUser(User user) {
        return 0;
    }

    @Override
    public int updateUser(User user) {
        return 0;
    }

    @Override
    public int deleteUser(UUID userId) {
        return 0;
    }
}
