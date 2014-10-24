package com.andrewcode.rest.Controllers;

import com.andrewcode.queue.Controllers.TaskQueue;
import com.andrewcode.queue.Utils.ProcessingFactory;
import com.andrewcode.queue.WorkItems.*;
import com.andrewcode.rest.Models.User;
import com.andrewcode.rest.Util.UserException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by andrew on 9/17/14.
 * LoginService.java
 */

@Path("/users")
public class UserService {

    private final static String queueName = "processing-queue";

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormParam("userId") Long userId,
                            @Context HttpServletRequest req) throws UserException, InterruptedException {
        IdLogin request = new IdLogin(userId, req);
        TaskQueue queue = ProcessingFactory.getTaskQueue(queueName);

        if (queue != null) {
            queue.add(request);
        }
        while (!request.isCompleted()) {
            Thread.sleep(5);
        }
        return request.getResponse();
    }

    @POST
    @Path("/login/username")
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUsername(@FormParam("username") String user,
                            @Context HttpServletRequest req) throws UserException, InterruptedException {
        UsernameLogin request = new UsernameLogin(user, req);
        TaskQueue queue = ProcessingFactory.getTaskQueue(queueName);

        if (queue != null) {
            queue.add(request);
        }
        while (!request.isCompleted()) {
            Thread.sleep(5);
        }
        return request.getResponse();
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(@FormParam("username") String user,
                                @FormParam("email") String email) throws UserException, InterruptedException {
        CreateUser request = new CreateUser(user, email);
        TaskQueue queue = ProcessingFactory.getTaskQueue(queueName);

        if (queue != null) {
            queue.add(request);
        }
        while (!request.isCompleted()) {
            Thread.sleep(5);
        }
        return request.getResponse();

    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsername(@PathParam("username") String username) throws InterruptedException {
        GetUsername request = new GetUsername(username);
        TaskQueue queue = ProcessingFactory.getTaskQueue(queueName);

        if (queue != null) {
            queue.add(request);
        }
        while (!request.isCompleted()) {
            Thread.sleep(5);
        }
        return request.getResponse();
    }

    @POST
    @Path("/logout")
    public String logout(@Context HttpServletRequest req) throws InterruptedException {
        Logout request = new Logout(req);
        TaskQueue queue = ProcessingFactory.getTaskQueue(queueName);

        if (queue != null) {
            queue.add(request);
        }
        while (!request.isCompleted()) {
            Thread.sleep(5);
        }
        return request.getResponse();
    }

}
