package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.Errors;
import com.andrewcode.rest.Models.Friends;
import com.andrewcode.rest.Models.Queue;
import com.andrewcode.rest.Models.User;
import com.andrewcode.rest.Util.FriendException;
import com.andrewcode.rest.Util.UserException;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by andrew on 10/24/14.
 * Login.java
 */
public class IdLogin implements WorkItem {

    boolean isProcessed = false;

    String response;
    HttpServletRequest req;
    Long userId;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public IdLogin(Long userId, HttpServletRequest req) {
        this.userId = userId;
        this.req = req;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean process() {
        final long startTime = System.currentTimeMillis();
        Long sessionUserId;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();

        User userEntity = (User) session.get(User.class, userId);

        if (userEntity == null) {
            // Log the exception
            Errors error = new Errors();
            error.setErrorCode(400);
            error.setDate(new Date());
            error.setException("UserException");
            error.setServerName(Utils.getServerName());
            session.save(error);
            transaction.commit();
            session.close();

            throw new UserException("User not found");
        } else {
            try {
                if (req == null) {
                    // Log the exception
                    Errors error = new Errors();
                    error.setErrorCode(400);
                    error.setDate(new Date());
                    error.setException("UserException");
                    error.setServerName(Utils.getServerName());
                    session.save(error);
                    transaction.commit();
                    session.close();

                    throw new UserException("Null request in context");
                } else {
                    HttpSession httpSession = req.getSession();
                    sessionUserId = (Long) httpSession.getAttribute("userId");
                    if (sessionUserId == null) {
                        sessionUserId = userEntity.getUserId();
                        httpSession.setAttribute("userId", sessionUserId);
                    }
                }
            } catch (Exception e) {
                // Log the exception
                Errors error = new Errors();
                error.setErrorCode(400);
                error.setDate(new Date());
                error.setException("UserException");
                error.setServerName(Utils.getServerName());
                session.save(error);
                transaction.commit();
                session.close();

                throw new UserException(e.getMessage());
            }
        }

        Queue queue = new Queue();
        queue.setTask(this.getClass().getSimpleName());
        queue.setTime(System.currentTimeMillis() - startTime);
        queue.setDate(new Date());
        queue.setServerName(Utils.getServerName());
        session.save(queue);

        transaction.commit();
        session.close();

        response = gson.toJson("Login Successful");

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