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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by andrew on 10/24/14.
 * CreateUser.java
 */
public class CreateUser implements WorkItem {

    boolean isProcessed = false;

    String response, user, email;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public CreateUser(String user, String email) {
        this.user = user;
        this.email = email;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean process() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        User existingUser = (User) session.createQuery("from User WHERE name= :name ").setParameter("name", user).uniqueResult();

        if (existingUser != null) {
            throw new UserException("Existing User with same username");
        }

        User userEntity = new User();
        userEntity.setName(user);
        userEntity.setEmail(email);

        Long userId = (Long)session.save(userEntity);

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