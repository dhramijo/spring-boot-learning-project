package co.uk.jdreamer.service;

import co.uk.jdreamer.repository.UserDao;
import co.uk.jdreamer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired // @Qualifier("fakeStudentDao") refer to @Repository("fakeDao") in FakeStudentDaoImpl
    public UserService(@Qualifier("userDao") UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAllUsers(Optional<String> gender) {
        List<User> users = userDao.selectAllUsers();
        if (!gender.isPresent()) {
            return users;
        }
        try {
            User.GENDER theGender = User.GENDER.valueOf(gender.get().toUpperCase());
            return users.stream()
                    .filter(user -> user.getGender().equals(theGender))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalStateException("Invalid gender",e);
        }
    }

    public Optional<User> getUserById(UUID userId) {
        return userDao.selectUserByUserUid(userId);
    }

    public int insertUser(User user) {
        UUID userUid = UUID.randomUUID();
        user.setUserUid(userUid);
        return userDao.insertUser(user);
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
