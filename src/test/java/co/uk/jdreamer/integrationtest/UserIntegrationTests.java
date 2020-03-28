package co.uk.jdreamer.integrationtest;

import co.uk.jdreamer.clientproxy.UserControllerRestEasyProxy;
import co.uk.jdreamer.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserIntegrationTests {

	@Autowired
	private UserControllerRestEasyProxy userControllerRestEasyProxy;

	@Test
	void test_shouldInsertUser() {
		// Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Jo", "Dreamer", User.GENDER.MALE, 22, "jdreamer@gmail.com");

		// When
		userControllerRestEasyProxy.insertUser(user);

		// Then
		User jo = userControllerRestEasyProxy.fetchUserById(userUid);
		assertThat(jo).isEqualToComparingFieldByField(user);

	}

	@Test
	void test_shouldDeleteUser() {
		// Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Jo", "Dreamer", User.GENDER.MALE, 22, "jdreamer@gmail.com");

		// When
		userControllerRestEasyProxy.insertUser(user);

		// Then
		User jo = userControllerRestEasyProxy.fetchUserById(userUid);
		assertThat(jo).isEqualToComparingFieldByField(user);

		// When
		userControllerRestEasyProxy.deleteUser(userUid);

		// Then
		assertThatThrownBy(() -> userControllerRestEasyProxy.fetchUserById(userUid))
				.isInstanceOf(NotFoundException.class);
	}

	@Test
	void test_shouldUpdateUser() {
		// Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Jo", "Dreamer", User.GENDER.MALE, 22, "jdreamer@gmail.com");

		// When
		userControllerRestEasyProxy.insertUser(user);

		User updatedUser = new User(userUid, "Anna", "Rossi", User.GENDER.FEMALE, 25, "ann_dreamer@gmail.com");

		userControllerRestEasyProxy.updateUser(updatedUser);

		// Then
		User anna = userControllerRestEasyProxy.fetchUserById(userUid);
		assertThat(anna).isEqualToComparingFieldByField(updatedUser);

	}

	@Test
	void test_shouldFetchUsersByGender() {
		// Given
		UUID userUid = UUID.randomUUID();
		User user = new User(userUid, "Jo", "Dreamer", User.GENDER.MALE, 22, "jdreamer@gmail.com");

		// When
		userControllerRestEasyProxy.insertUser(user);

		// Then
		List<User> females = userControllerRestEasyProxy.fetchUsers(User.GENDER.FEMALE.name());
		assertThat(females).extracting("userUid").doesNotContain(userUid);
		assertThat(females).extracting("firstName").doesNotContain(user.getFirstName());
		assertThat(females).extracting("lastName").doesNotContain(user.getLastName());
		assertThat(females).extracting("gender").doesNotContain(user.getGender());
		assertThat(females).extracting("age").doesNotContain(user.getAge());
		assertThat(females).extracting("email").doesNotContain(user.getEmail());

		List<User> males = userControllerRestEasyProxy.fetchUsers(User.GENDER.MALE.name());
		assertThat(males).extracting("userUid").contains(userUid);
		assertThat(males).extracting("firstName").contains(user.getFirstName());
		assertThat(males).extracting("lastName").contains(user.getLastName());
		assertThat(males).extracting("gender").contains(user.getGender());
		assertThat(males).extracting("age").contains(user.getAge());
		assertThat(males).extracting("email").contains(user.getEmail());
	}
}
