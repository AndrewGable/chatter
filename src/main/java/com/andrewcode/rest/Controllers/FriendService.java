package com.andrewcode.rest.Controllers;

import com.andrewcode.rest.Models.Friends;
import com.andrewcode.rest.Util.FriendException;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andrew on 9/17/14.
 * FriendService.java
 */


@Path("/")
public class FriendService {

    SessionFactory sessionFactory = Utils.createSessionFactory();

    @GET
    @Path("/friendships/incoming")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public String getIncomingFriends(@Context HttpServletRequest req){
        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();

        Long userId = Utils.getUserId(req);

        List<Friends> list = session.createQuery("from Friends WHERE friendsId= :id ").setParameter("id", userId).list();

        ArrayList<Long> incomingFriends = new ArrayList<Long>();

        for(Friends friend : list){
            if(friend.getAccepted().equals("N")) {
                incomingFriends.add(friend.getUserId());
            }
        }

        session.close();

        return gson.toJson(incomingFriends);
    }

    @GET
    @Path("/friendships/outgoing")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public String getOutgoingFriends(@Context HttpServletRequest req) {
        Session session = sessionFactory.openSession();

        Long userId = Utils.getUserId(req);

        List<Friends> list = session.createQuery("from Friends WHERE userId= :id ").setParameter("id", userId).list();

        ArrayList<Long> outgoingFriends = new ArrayList<Long>();

        for(Friends friend : list){
            if(friend.getAccepted().equals("N")){
                outgoingFriends.add(friend.getFriendsId());
            }
        }

        session.close();
        Gson gson = new GsonBuilder().create();

        return gson.toJson(outgoingFriends);
    }

    @POST
    @Path("/friendships/create")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public String createFriendship(@FormParam("friend_id") long friendId,
                                    @Context HttpServletRequest req) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        Long userId = Utils.getUserId(req);

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
                    return gson.toJson(friendId);
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

        return gson.toJson(friendId);
    }

    @POST
    @Path("/friendships/destroy")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeFriendship(@FormParam("friend_id") long friendId,
                                   @Context HttpServletRequest req) {

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        Long userId = Utils.getUserId(req);

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

        return gson.toJson(friendId);
    }

    @GET
    @Path("/friends/list")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public String getFollowing(@Context HttpServletRequest req) {
        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();
        Long userId = Utils.getUserId(req);

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

        return gson.toJson(following);
    }

    @GET
    @Path("/followers/list")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public String getFollowers(@Context HttpServletRequest req) {
        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();
        Long userId = Utils.getUserId(req);

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
        session.close();

        return gson.toJson(followers);
    }
}
