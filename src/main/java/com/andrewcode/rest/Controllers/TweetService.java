package com.andrewcode.rest.Controllers;

import com.andrewcode.queue.Controllers.TaskQueue;
import com.andrewcode.queue.Utils.ProcessingFactory;
import com.andrewcode.queue.WorkItems.*;
import com.andrewcode.rest.Util.Utils;
import org.hibernate.SessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by andrew on 9/17/14.
 * TweetService.java
 */

@Path("/tweet")
public class TweetService {

    private final static String queueName = "processing-queue";

    @POST
    @Path("/tweet")
    @Produces(MediaType.APPLICATION_JSON)
    public String tweet(@FormParam("message") String message,
                          @Context HttpServletRequest req) throws InterruptedException {
        Long userId = Utils.getUserId(req);
        PostTweet request = new PostTweet(message, userId);
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
    @Path("/show/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTweet(@PathParam("id") Long id) throws InterruptedException {
        GetTweet request = new GetTweet(id);
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
    @Path("/destroy")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeTweet(@FormParam("id") Long id,
                              @Context HttpServletRequest req) throws InterruptedException {
        Long userId = Utils.getUserId(req);
        DestroyTweet request = new DestroyTweet(id, userId);
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
    @Path("/retweet")
    @Produces(MediaType.APPLICATION_JSON)
    public String retweetTweet(@FormParam("id") Long id,
                                @Context HttpServletRequest req) throws InterruptedException {
        Long userId = Utils.getUserId(req);
        RetweetTweet request = new RetweetTweet(id, userId);
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
    @Path("/getTweets")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTweets(@Context HttpServletRequest req) throws InterruptedException {
        Long userId = Utils.getUserId(req);
        GetTweetList request = new GetTweetList(userId);
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
