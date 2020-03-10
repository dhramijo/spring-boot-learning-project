package co.uk.jdreamer.service;

import co.uk.jdreamer.dao.UserDao;
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
    public UserService(@Qualifier("fakeUserDao") UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllStudents() {
        return userDao.selectAllUsers();
    }

    public Optional<User> getUserById(UUID userId) {
        return userDao.selectUserByUserUid(userId);
    }

    public int insertUser(User user) {
        return userDao.insertUser(UUID.randomUUID(), user);
    }

    public int updateStudentById(User user) {
        Optional<User> optionalUser = getUserById(user.getUserUid());
        if (optionalUser.isPresent()) {
            userDao.updateUser(user);
        }
        return -1;
    }

    public int deleteStudentById(UUID userId) {
        Optional<User> optionalUser = getUserById(userId);
        if (optionalUser.isPresent()) {
            userDao.deleteUserByUserUid(userId);
        }
        return -1;
    }

}
