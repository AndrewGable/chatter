package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.Errors;
import com.andrewcode.rest.Models.Queue;
import com.andrewcode.rest.Models.Tweet;
import com.andrewcode.rest.Util.TweetException;
import com.andrewcode.rest.Util.Utils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;

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
        final long startTime = System.currentTimeMillis();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Tweet tweetEntity = (Tweet)session.get(Tweet.class, tweetId);

        if (tweetEntity == null) {
            // Log the exception
            Errors error = new Errors();
            error.setErrorCode(400);
            error.setDate(new Date());
            error.setException("TweetException");
            error.setServerName(Utils.getServerName());
            session.save(error);
            transaction.commit();
            session.close();

            throw new TweetException("Tweet does not exist.");
        }

        Queue queue = new Queue();
        queue.setTask(this.getClass().getSimpleName());
        queue.setTime(System.currentTimeMillis() - startTime);
        queue.setDate(new Date());
        queue.setServerName(Utils.getServerName());
        session.save(queue);

        transaction.commit();
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