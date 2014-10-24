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
 * Created by andrew on 10/24/14.
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

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        Tweet tweetEntity = (Tweet)session.get(Tweet.class, tweetId);

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