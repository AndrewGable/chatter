package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.Friends;
import com.andrewcode.rest.Models.Queue;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by andrew on 10/24/14.
 * GetFollowers.java
 */
public class GetFollowers implements WorkItem {

    boolean isProcessed = false;

    String response;
    Long userId;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public GetFollowers(Long userId) {
        this.userId = userId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean process() {
        final long startTime = System.currentTimeMillis();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        List<Friends> list = session.createQuery("from Friends WHERE userId= :id or friendsId= :id").setParameter("id", userId).list();

        ArrayList<Long> followers = new ArrayList<Long>();

        for (Friends friend : list) {
            if (friend.getAccepted().equals("Y") && friend.getUserId() == userId) {
                //use friendId
                followers.add(friend.getFriendsId());
            } else if (friend.getAccepted().equals("Y") && friend.getFriendsId() == userId) {
                //use userId
                followers.add(friend.getUserId());
            } else if (friend.getAccepted().equals("N") && friend.getUserId() != userId) {
                //use userId, not following back
                followers.add(friend.getUserId());
            }
        }

        Queue queue = new Queue();
        queue.setTask(this.getClass().getSimpleName());
        queue.setTime(System.currentTimeMillis() - startTime);
        queue.setDate(new Date());
        session.save(queue);

        transaction.commit();
        session.close();

        response = gson.toJson(followers);

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