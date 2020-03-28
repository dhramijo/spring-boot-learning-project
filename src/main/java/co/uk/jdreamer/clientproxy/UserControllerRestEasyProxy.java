package co.uk.jdreamer.clientproxy;

import co.uk.jdreamer.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public interface UserControllerRestEasyProxy {

    @GET
    @Produces(APPLICATION_JSON)
    List<User> fetchUsers(@QueryParam("gender") String gender);

    @GET
    @Produces(APPLICATION_JSON)
    @Path("{userId}")
    User fetchUserById(@PathParam("userId") UUID userId);

    @POST
    @Consumes(APPLICATION_JSON)
    void insertUser(User newUser);

    @PUT
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    void updateUser(User newUser);

    @DELETE
    @Produces(APPLICATION_JSON)
    @Path("{userId}")
    void deleteUser(@PathParam("userId") UUID userId);

}
