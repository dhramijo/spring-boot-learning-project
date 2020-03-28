package co.uk.jdreamer.controller;

import co.uk.jdreamer.model.User;
import co.uk.jdreamer.service.UserService;
import co.uk.jdreamer.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Validated
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

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{userId}")
    public User fetchUserById(@PathParam("userId") UUID userId) {
        return userService
                .getUserById(userId)
                .orElseThrow(() -> new NotFoundException("User: " + userId + " not found"));
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public void insertUser(@Valid User newUser) {
        userService.insertUser(newUser);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public void updateUser(@Valid User newUser) {
        userService.updateUser(newUser);
    }

    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("{userId}")
    public void deleteUser(@PathParam("userId") UUID userId) {
        userService.deleteUserById(userId);
    }

    private Response getIntegerResponseEntity(int resultInsert) {
        if (resultInsert == 1) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
