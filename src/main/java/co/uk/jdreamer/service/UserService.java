package co.uk.jdreamer.service;

import co.uk.jdreamer.model.User;
import co.uk.jdreamer.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
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
        UUID userUid = user.getUserUid() == null ? UUID.randomUUID() : user.getUserUid();
        return userDao.insertUser(userUid, User.newUser(userUid, user));
    }

    public int updateUser(User user) {
        Optional<User> optionalUser = getUserById(user.getUserUid());
        if (optionalUser.isPresent()) {
            return userDao.updateUser(user);
        }
        throw new NotFoundException("User: " + user.getUserUid() + " not found");
    }

    public int deleteUserById(UUID uId) {
        UUID userUid = getUserById(uId)
                .map(User::getUserUid)
                .orElseThrow(() -> new NotFoundException("User: " + uId + " not found"));;
            return userDao.deleteUserByUserUid(userUid);
    }

}
