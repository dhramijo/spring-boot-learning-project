package co.uk.jdreamer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private final UUID userUid;

    @NotNull
    private final String firstName;

    @NotNull
    private final String lastName;

    @NotNull
    private final GENDER gender;

    @NotNull
    @Max(value = 112)
    @Min(value = 0)
    private final Integer age;

    @NotNull
    @Email
    private final String email;

    public enum GENDER {
        MALE,FEMALE
    }

    public User(
            @JsonProperty("userUid") UUID userUid,
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("gender") GENDER gender,
            @JsonProperty("age") Integer age,
            @JsonProperty("email") String email) {
        this.userUid = userUid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.age = age;
        this.email = email;
    }

    // @JsonProperty("id") @JsonProperty to define the property name to send to the server
    public UUID getUserUid() {
        return userUid;
    }

    // @JsonIgnore, to hide property to the client
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public GENDER getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    /* Computed JSON properties
       Additional custom properties added to the JSON structure
    */
    public String getFullName() {
        return firstName + " " +lastName;
    }

    public int getDateOfBirth() {
        return LocalDate.now().minusYears(age).getYear();
    }

    public static User newUser(UUID userUid, User user) {
        return new User(userUid, user.getFirstName(), user.getLastName(),
                user.getGender(), user.getAge(), user.getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "userUid=" + userUid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", age=" + age +
                ", email='" + email + '\'' +
                '}';
    }
}
