package co.uk.jdreamer.controller;

import co.uk.jdreamer.model.User;
import co.uk.jdreamer.service.UserService;
import co.uk.jdreamer.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{userId}")
    public Response fetchUserById(@PathParam("userId") UUID userId) {
        Optional<User> optionalUser = userService.getUserById(userId);
        if (optionalUser.isPresent()) {
            return Response.ok(optionalUser.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage("User: " + userId + " was not found"))
                .build();
    }

    @POST
    @Consumes(APPLICATION_JSON)
    public Response insertUser(User newUser) {
        int result = userService.insertUser(newUser);
        return getIntegerResponseEntity(result);
    }

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public Response updateUser(User newUser) {
        int result = userService.updateUser(newUser);
        return getIntegerResponseEntity(result);
    }

    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("{userId}")
    public Response deleteUser(@PathParam("userId") UUID userId) {
        int result = userService.deleteUserById(userId);
        return getIntegerResponseEntity(result);
    }

    private Response getIntegerResponseEntity(int resultInsert) {
        if (resultInsert == 1) {
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
