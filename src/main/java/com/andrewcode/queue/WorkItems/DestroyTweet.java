package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.Errors;
import com.andrewcode.rest.Models.Queue;
import com.andrewcode.rest.Models.Tweet;
import com.andrewcode.rest.Util.TweetException;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;

/**
 * Created by andrew on 10/24/14.
 * DestroyTweet.java
 */
public class DestroyTweet implements WorkItem {

    boolean isProcessed = false;

    String response;
    Long tweetId, userId;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public DestroyTweet(Long tweetId, Long userId) {
        this.tweetId = tweetId;
        this.userId = userId;
    }

    @Override
    public boolean process() {
        final long startTime = System.currentTimeMillis();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        Tweet tweetEntity = (Tweet)session.get(Tweet.class, tweetId);

        if (tweetEntity == null) {
            // Log the exception
            Errors error = new Errors();
            error.setErrorCode(400);
            error.setDate(new Date());
            error.setException("TweetException");
            session.save(error);
            transaction.commit();
            session.close();

            throw new TweetException("Tweet does not exist.");
        }

        if (userId != tweetEntity.getUserId()) {
            // Log the exception
            Errors error = new Errors();
            error.setErrorCode(400);
            error.setDate(new Date());
            error.setException("TweetException");
            session.save(error);
            transaction.commit();
            session.close();

            throw new TweetException("Must be user who created tweet to delete.");
        } else {
            session.delete(tweetEntity);
        }

        Queue queue = new Queue();
        queue.setTask(this.getClass().getSimpleName());
        queue.setTime(System.currentTimeMillis() - startTime);
        queue.setDate(new Date());
        session.save(queue);

        transaction.commit();
        session.close();

        response =  gson.toJson(tweetEntity.getTweetId());

        isProcessed = true;
        return isProcessed;
    }

    public boolean isCompleted(){
        return isProcessed;
    }

    public String getResponse(){
        return response;
    }

}