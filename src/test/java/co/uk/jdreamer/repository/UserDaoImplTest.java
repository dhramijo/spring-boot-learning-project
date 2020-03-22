package co.uk.jdreamer.repository;

import co.uk.jdreamer.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoImplTest {

    private UserDaoImpl fakeUserDao;

    @BeforeEach
    void setUp() {
        fakeUserDao = new UserDaoImpl();
    }

    @Test
    void test_shouldSelectAllUsers() {

        List<User> users = fakeUserDao.selectAllUsers();

        assertThat(users).hasSize(1);

        User user = users.get(0);

        assertThat(user.getUserUid()).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("Jo");
        assertThat(user.getLastName()).isEqualTo("Dreamer");
        assertThat(user.getGender()).isEqualTo(User.GENDER.MALE);
        assertThat(user.getAge()).isEqualTo(22);
        assertThat(user.getEmail()).isEqualTo("jdreamer@gmail.com");

    }

    @Test
    void test_shouldSelectUserByUserUid() {

        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,"anna","montana",
                User.GENDER.FEMALE,30,"anna@gmail.com");

        fakeUserDao.insertUser(anna);

        assertThat(fakeUserDao.selectAllUsers()).hasSize(2);

        Optional<User> annaOptional = fakeUserDao.selectUserByUserUid(annaUid);

        assertThat(annaOptional.isPresent()).isTrue();
        assertThat(annaOptional.get()).isEqualToComparingFieldByField(anna);
    }

    @Test
    void test_shouldNotSelectUserByRandomUserUid() {
        UUID randomUUID = UUID.randomUUID();

        Optional<User> user = fakeUserDao.selectUserByUserUid(randomUUID);

        assertThat(user.isPresent()).isFalse();
    }

    @Test
    void test_shouldInsertUser() {

        UUID userUid = UUID.randomUUID();
        User user = new User(userUid,"anna","montana",
                User.GENDER.FEMALE,30,"anna@gmail.com");

        fakeUserDao.insertUser(user);

        List<User> users = fakeUserDao.selectAllUsers();

        assertThat(users).hasSize(2);
        assertThat(fakeUserDao.selectUserByUserUid(userUid).get()).isEqualToComparingFieldByField(user);
    }

    @Test
    void test_shouldUpdateUser() {

        UUID joeUserUid = fakeUserDao.selectAllUsers().get(0).getUserUid();
        User newJoe = new User(joeUserUid,"anna","montana",
                User.GENDER.FEMALE,30,"anna@gmail.com");

        fakeUserDao.updateUser(newJoe);
        Optional<User> optionalNewJoe = fakeUserDao.selectUserByUserUid(joeUserUid);

        assertThat(optionalNewJoe.isPresent()).isTrue();
        assertThat(fakeUserDao.selectAllUsers()).hasSize(1);
        assertThat(optionalNewJoe.get()).isEqualToComparingFieldByField(newJoe);
    }

    @Test
    void test_shouldDeleteUserByUserUid() {
        UUID joeUserUid = fakeUserDao.selectAllUsers().get(0).getUserUid();
        fakeUserDao.deleteUserByUserUid(joeUserUid);

        assertThat(fakeUserDao.selectUserByUserUid(joeUserUid).isPresent()).isFalse();
        assertThat(fakeUserDao.selectAllUsers()).isEmpty();
    }
}