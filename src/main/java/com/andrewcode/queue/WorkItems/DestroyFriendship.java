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

import java.util.List;

/**
 * Created by andrew on 10/24/14.
 * DestroyFriendship.java
 */
public class DestroyFriendship implements WorkItem {

    boolean isProcessed = false;

    String response;
    Long userId, friendId;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public DestroyFriendship(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean process() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        if (userId == friendId) {
            throw new FriendException("Cannot remove yourself as a friend.");
        }

        Friends friend = (Friends) session.createQuery("from Friends WHERE (userId= :id and friendsId = :friendId) " +
                "or (userId= :friendId and friendsId = :id) ").setParameter("id", userId).setParameter("friendId", friendId).uniqueResult();

        if (friend == null) {
            throw new FriendException("Cannot remove friend that doesn't exist.");
        } else {
            //Other friend is not following back, just remove friendship
            session.delete(friend);
        }

        transaction.commit();
        session.close();

        response = gson.toJson(friendId);

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