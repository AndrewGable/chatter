package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.Tweet;
import com.andrewcode.rest.Util.TweetException;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by andrew on 10/23/14.
 * PostTweet.java
 */
public class PostTweet implements WorkItem {

    boolean isProcessed = false;

    String response,  message;
    Long userId;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public PostTweet(String message, Long userId) {
        this.message = message;
        this.userId = userId;
    }

    @Override
    public boolean process() {

        if (message.length() > 128) {
            throw new TweetException("Tweet is too long, must be less that 128 characters.");
        }

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        Tweet tweet = new Tweet();
        tweet.setMessage(message);
        tweet.setUserId(userId);

        Long tweetId = (Long)session.save(tweet);

        transaction.commit();
        session.close();

        response = gson.toJson(tweetId);

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
