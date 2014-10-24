package com.andrewcode.rest.Controllers;

import com.andrewcode.queue.Controllers.TaskQueue;
import com.andrewcode.queue.Utils.ProcessingFactory;
import com.andrewcode.queue.WorkItems.*;
import com.andrewcode.rest.Models.Friends;
import com.andrewcode.rest.Util.FriendException;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 9/17/14.
 * FriendService.java
 */

@Path("/")
public class FriendService {

    private final static String queueName = "processing-queue";

    @GET
    @Path("/friendships/incoming")
    @Produces(MediaType.APPLICATION_JSON)
    public String getIncomingFriends(@Context HttpServletRequest req) throws InterruptedException {
        Long userId = Utils.getUserId(req);
        GetIncomingFriends request = new GetIncomingFriends(userId);
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
    @Path("/friendships/outgoing")
    @Produces(MediaType.APPLICATION_JSON)
    public String getOutgoingFriends(@Context HttpServletRequest req) throws InterruptedException {
        Long userId = Utils.getUserId(req);
        GetOutgoingFriends request = new GetOutgoingFriends(userId);
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
    @Path("/friendships/create")
    @Produces(MediaType.APPLICATION_JSON)
    public String createFriendship(@FormParam("friend_id") long friendId,
                                    @Context HttpServletRequest req) throws InterruptedException {
        Long userId = Utils.getUserId(req);
        CreateFriendship request = new CreateFriendship(userId, friendId);
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
    @Path("/friendships/destroy")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeFriendship(@FormParam("friend_id") long friendId,
                                   @Context HttpServletRequest req) throws InterruptedException {
        Long userId = Utils.getUserId(req);
        DestroyFriendship request = new DestroyFriendship(userId, friendId);
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
    @Path("/friends/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFollowing(@Context HttpServletRequest req) throws InterruptedException {
        Long userId = Utils.getUserId(req);
        GetFollowing request = new GetFollowing(userId);
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
    @Path("/followers/list")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFollowers(@Context HttpServletRequest req) throws InterruptedException {
        Long userId = Utils.getUserId(req);
        GetFollowers request = new GetFollowers(userId);
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
