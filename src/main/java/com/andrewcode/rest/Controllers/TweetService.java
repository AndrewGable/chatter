package com.andrewcode.rest.Controllers;

import com.andrewcode.rest.Models.Friends;
import com.andrewcode.rest.Models.Tweet;
import com.andrewcode.rest.Util.TweetException;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
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
 * TweetService.java
 */

@Path("/tweet")
public class TweetService {

    SessionFactory sessionFactory = Utils.createSessionFactory();

    @POST
    @Path("/tweet")
    @Produces(MediaType.APPLICATION_JSON)
    public String tweet(@FormParam("message") String message,
                          @Context HttpServletRequest req) {
        if (message.length() > 128) {
            throw new TweetException("Tweet is too long, must be less that 128 characters.");
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        Long userId = Utils.getUserId(req);

        Tweet tweet = new Tweet();
        tweet.setMessage(message);
        tweet.setUserId(userId);

        Long tweetId = (Long)session.save(tweet);

        transaction.commit();
        session.close();

        return gson.toJson(tweetId);
    }

    @GET
    @Path("/show/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getTweet(@PathParam("id") Long id){
        Session session = sessionFactory.openSession();

        Tweet tweetEntity = (Tweet)session.get(Tweet.class, id);

        if (tweetEntity == null) {
            throw new TweetException("Tweet does not exist.");
        }

        session.close();
        return tweetEntity.getMessage();
    }

    @POST
    @Path("/destroy")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeTweet(@FormParam("id") Long id,
                              @Context HttpServletRequest req) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        Long userId = Utils.getUserId(req);

        Tweet tweetEntity = (Tweet)session.get(Tweet.class, id);

        if (tweetEntity == null) {
            throw new TweetException("Tweet does not exist.");
        }

        if (userId != tweetEntity.getUserId()) {
            throw new TweetException("Must be user who created tweet to delete.");
        } else {
            session.delete(tweetEntity);
        }

        transaction.commit();
        session.close();

        return gson.toJson(tweetEntity.getTweetId());
    }

    @POST
    @Path("/retweet")
    @Produces(MediaType.APPLICATION_JSON)
    public String retweetTweet(@FormParam("id") Long id,
                                @Context HttpServletRequest req) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        Long userId = Utils.getUserId(req);

        Tweet tweetEntity = (Tweet)session.get(Tweet.class, id);

        if (tweetEntity == null) {
            throw new TweetException("Tweet does not exist.");
        }

        Tweet retweet = new Tweet();
        retweet.setMessage(tweetEntity.getMessage());
        retweet.setUserId(userId);

        Long tweetId = (Long)session.save(retweet);

        transaction.commit();
        session.close();

        return gson.toJson(tweetId);
    }

    @GET
    @Path("/getTweets")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public String getTweets(@Context HttpServletRequest req) {
        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();

        Long userId = Utils.getUserId(req);

        List<Friends> list = session.createQuery("from Friends WHERE userId= :id or friendsId= :id").setParameter("id", userId).list();

        ArrayList<Long> following = new ArrayList<Long>();

        for(Friends friend : list) {
            if (friend.getUserId() == userId) {
                //Use the friendId
                following.add(friend.getFriendsId());
            } else if (friend.getAccepted().equals("Y")) {
                //Use the userId & check to see if they are following each other
                following.add(friend.getUserId());
            }
        }
        following.add(userId);

        List<Tweet> tweets = (List<Tweet>) session.createQuery("from Tweet WHERE userId IN (:id)").setParameterList("id", following).list();

        session.close();

        return gson.toJson(tweets);
    }
}
