package org.harsh.controller;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.harsh.dto.UserDTO;
import org.harsh.service.AuthService;
import org.harsh.utils.JwtTokenGenerator;

import java.util.Map;

@Path("/api/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
@ApplicationScoped
public class AuthController {
    @Inject
    private AuthService authService;

    @Inject
    private JwtTokenGenerator jwtTokenGenerator;

    @POST
    @Path("/register")
    public Response register(UserDTO userPayload) {
        try{
            UserDTO registerUser = authService.registerUser(userPayload);
            String jwtToken = jwtTokenGenerator.generateToken(registerUser.getEmail());
            return Response.status(Response.Status.CREATED)
                    .entity(registerUser)
                    .header("Authorization", "Bearer " + jwtToken)
                    .build();
        }
        catch(Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("User registration failed").build();
        }
    }

    @POST
    @Path("/login")
    public Response login(Map<String,String> loginPayload) {
        String email = loginPayload.get("email");
        String password = loginPayload.get("password");

        try{
            UserDTO loggedInUser = authService.loginUser(email, password);
            String jwtToken = jwtTokenGenerator.generateToken(loggedInUser.getEmail());
            return Response.ok()
                    .entity(loggedInUser)
                    .header("Authorization", "Bearer " + jwtToken)
                    .build();
        }
        catch(Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid email or password").build();
        }
    }
}
