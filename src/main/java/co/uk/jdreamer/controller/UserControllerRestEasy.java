package co.uk.jdreamer.controller;

import co.uk.jdreamer.model.User;
import co.uk.jdreamer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Optional;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Component
@Path("api/v1/users")
public class UserControllerRestEasy {

    private UserService userService;

    @Autowired
    public UserControllerRestEasy(UserService userService) {
        this.userService = userService;
    }

    @GET
    @Produces(APPLICATION_JSON)
    public List<User> fetchUsers(@QueryParam("gender") String gender) {
        // filter result by gender
        return userService.getAllUsers(Optional.ofNullable(gender));
    }

}
