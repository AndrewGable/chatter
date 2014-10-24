package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.Friends;
import com.andrewcode.rest.Util.FriendException;
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
 * GetFollowing.java
 */
public class GetFollowing implements WorkItem {

    boolean isProcessed = false;

    String response;
    Long userId;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public GetFollowing(Long userId) {
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
            if(friend.getUserId() == userId){
                //Use the friendId
                following.add(friend.getFriendsId());
            } else if (friend.getAccepted().equals("Y")) {
                //Use the userId & check to see if they are following each other
                following.add(friend.getUserId());
            }
        }

        session.close();

        response = gson.toJson(following);;

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