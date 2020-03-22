package co.uk.jdreamer.service;

import co.uk.jdreamer.repository.UserDao;
import co.uk.jdreamer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired // @Qualifier("fakeStudentDao") refer to @Repository("fakeDao") in FakeStudentDaoImpl
    public UserService(@Qualifier("userDao") UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers() {
        return userDao.selectAllUsers();
    }

    public Optional<User> getUserById(UUID userId) {
        return userDao.selectUserByUserUid(userId);
    }

    public void insertUser(User user) {
        UUID userUid = UUID.randomUUID();
        user.setUserUid(userUid);
        userDao.insertUser(user);
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUserById(user.getUserUid());
        if (optionalUser.isPresent()) {
            return userDao.updateUser(user);
        }
        return -1;
    }

    public int deleteUserById(UUID userId) {
        Optional<User> optionalUser = getUserById(userId);
        if (optionalUser.isPresent()) {
            return userDao.deleteUserByUserUid(userId);
        }
        return -1;
    }

}
