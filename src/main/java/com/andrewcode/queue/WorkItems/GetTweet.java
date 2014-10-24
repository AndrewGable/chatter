package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.Tweet;
import com.andrewcode.rest.Util.TweetException;
import com.andrewcode.rest.Util.Utils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by andrew on 10/24/14.
 * GetTweet.java
 */
public class GetTweet implements WorkItem {

    boolean isProcessed = false;

    String response;
    Long tweetId;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public GetTweet(Long tweetId) {
        this.tweetId = tweetId;
    }

    @Override
    public boolean process() {

        Session session = sessionFactory.openSession();

        Tweet tweetEntity = (Tweet)session.get(Tweet.class, tweetId);

        if (tweetEntity == null) {
            throw new TweetException("Tweet does not exist.");
        }

        session.close();

        response =  tweetEntity.getMessage();

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