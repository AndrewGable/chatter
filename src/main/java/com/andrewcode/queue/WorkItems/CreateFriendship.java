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
 * CreateFriendship.java
 */
public class CreateFriendship implements WorkItem {

    boolean isProcessed = false;

    String response;
    Long userId, friendId;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public CreateFriendship(Long userId, Long friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean process() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        if(userId == friendId){
            throw new FriendException("Cannot add yourself as a friend.");
        }

        List<Friends> list = session.createQuery("from Friends WHERE friendsId= :id ").setParameter("id", userId).list();

        if (!list.isEmpty()) {
            for (Friends friend : list) {
                if (friend.getUserId() == friendId && !friend.getAccepted().equals("Y")) {
                    //Accept friendship
                    friend.setAccepted("Y");
                    session.save(friend);
                    transaction.commit();
                    session.close();

                    response = gson.toJson(friendId);

                    isProcessed = true;
                    return isProcessed;
                } else if (friend.getFriendsId() == friendId && friend.getAccepted().equals("Y")) {
                    //Duplicate friend
                    throw new FriendException("Duplicate friend request to same person.");
                }
            }
            //No friendship created yet
            Friends friend = new Friends();
            friend.setUserId(userId);
            friend.setFriendsId(friendId);
            friend.setAccepted("N");
            session.save(friend);
        } else {
            //No friendship created yet
            Friends friend = new Friends();
            friend.setUserId(userId);
            friend.setFriendsId(friendId);
            friend.setAccepted("N");
            session.save(friend);
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