package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.User;
import com.andrewcode.rest.Util.UserException;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by andrew on 10/24/14.
 * GetUsername.java
 */
public class GetUsername implements WorkItem {

    boolean isProcessed = false;

    String response, user;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public GetUsername(String user) {
        this.user = user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean process() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();
        Long userId;

        User existingUser = (User) session.createQuery("from User WHERE name= :name ").setParameter("name", user).uniqueResult();

        if (existingUser == null) {
            throw new UserException("User doesn't exist with that username");
        } else {
            userId = existingUser.getUserId();
        }

        transaction.commit();
        session.close();

        response = gson.toJson(userId);

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