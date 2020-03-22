package co.uk.jdreamer.service;

import co.uk.jdreamer.model.User;
import co.uk.jdreamer.repository.UserDaoImpl;

import jersey.repackaged.com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;
import org.mockito.MockitoAnnotations;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

class UserServiceTest {

    @Mock
    private UserDaoImpl fakeUserDao;

    private UserService userService;

    @Captor
    ArgumentCaptor<User> captor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userService = new UserService(fakeUserDao);
    }

    @Test
    void  test_shouldGetAllStudents() {

        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,"anna","montana",
                User.GENDER.FEMALE,30,"anna@gmail.com");

        ImmutableList<User> users = new ImmutableList.Builder<User>()
                .add(anna)
                .build();

        // GIVEN mock the dao
        given(fakeUserDao.selectAllUsers()).willReturn(users);

        List<User> allUsers = userService.getAllUsers();

        User user = allUsers.get(0);

        assertThat(allUsers).hasSize(1);
        assertThat(user.getUserUid()).isNotNull();
        assertThat(user.getFirstName()).isEqualTo("anna");
        assertThat(user.getLastName()).isEqualTo("montana");
        assertThat(user.getGender()).isEqualTo(User.GENDER.FEMALE);
        assertThat(user.getAge()).isEqualTo(30);
        assertThat(user.getEmail()).isEqualTo("anna@gmail.com");

    }

    @Test
    void test_shouldGetUserById() {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,"anna","montana",
                User.GENDER.FEMALE,30,"anna@gmail.com");

        given(fakeUserDao.selectUserByUserUid(annaUid)).willReturn(java.util.Optional.of(anna));

        Optional<User> user = userService.getUserById(annaUid);

        assertThat(user.isPresent()).isTrue();
    }

    @Test
    void test_shouldInsertUser() {

        User anna = new User(null,"anna","montana",
                User.GENDER.FEMALE,30,"anna@gmail.com");

        given(fakeUserDao.insertUser(anna)).willReturn(1);

        int insertResult = userService.insertUser(anna);

        verify(fakeUserDao, times(1)).insertUser(captor.capture());

        assertEquals(captor.getValue().getLastName(), "montana");

        /* Test per void insert method

        // Dao call to void insertUser method instead of given(fakeUserDao.insertUser(anna)).willReturn(1);
        doNothing().when(fakeUserDao).insertUser(any(User.class));

         */

    }

    @Test
    void test_shouldUpdateUser() {
        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,"anna","montana",
                User.GENDER.FEMALE,30,"anna@gmail.com");

        given(fakeUserDao.selectUserByUserUid(annaUid)).willReturn(java.util.Optional.of(anna));
        given(fakeUserDao.updateUser(anna)).willReturn(1);

        int updateResult = userService.updateUser(anna);

        verify(fakeUserDao).selectUserByUserUid(annaUid);

        assertThat(updateResult).isEqualTo(1);
    }


    @Test
    void test_shouldDeleteUserById() {

        UUID annaUid = UUID.randomUUID();
        User anna = new User(annaUid,"anna","montana",
                User.GENDER.FEMALE,30,"anna@gmail.com");

        given(fakeUserDao.selectUserByUserUid(annaUid)).willReturn(java.util.Optional.of(anna));
        given(fakeUserDao.deleteUserByUserUid(annaUid)).willReturn(1);

        int deleteResult = userService.deleteUserById(annaUid);

        verify(fakeUserDao).selectUserByUserUid(annaUid);

        assertThat(deleteResult).isEqualTo(1);
    }
}