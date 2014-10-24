package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.Friends;
import com.andrewcode.rest.Models.Tweet;
import com.andrewcode.rest.Util.TweetException;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 10/24/14.
 * GetIncomingFriends.java
 */
public class GetIncomingFriends implements WorkItem {

    boolean isProcessed = false;

    String response;
    Long userId;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public GetIncomingFriends(Long userId) {
        this.userId = userId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean process() {

        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();

        List<Friends> list = session.createQuery("from Friends WHERE friendsId= :id ").setParameter("id", userId).list();

        ArrayList<Long> incomingFriends = new ArrayList<Long>();

        for(Friends friend : list){
            if(friend.getAccepted().equals("N")) {
                incomingFriends.add(friend.getUserId());
            }
        }

        session.close();

        response = gson.toJson(incomingFriends);

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