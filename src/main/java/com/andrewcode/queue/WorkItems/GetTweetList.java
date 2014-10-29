package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.Friends;
import com.andrewcode.rest.Models.Tweet;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 10/24/14.
 * GetTweetList.java
 */
public class GetTweetList implements WorkItem {

    boolean isProcessed = false;

    String response;
    Long userId;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public GetTweetList( Long userId) {
        this.userId = userId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean process() {
        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();

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

        List<Tweet> tweets = (List<Tweet>) session.createQuery("from Tweet WHERE userId IN (:id) ORDER BY tweetId desc").setParameterList("id", following).list();

        session.close();

        response =  gson.toJson(tweets);

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