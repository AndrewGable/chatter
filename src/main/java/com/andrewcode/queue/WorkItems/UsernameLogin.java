package com.andrewcode.queue.WorkItems;

import com.andrewcode.queue.Utils.WorkItem;
import com.andrewcode.rest.Models.User;
import com.andrewcode.rest.Util.UserException;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by andrew on 10/24/14.
 * UsernameLogin.java
 */
public class UsernameLogin implements WorkItem {

    boolean isProcessed = false;

    String response;
    HttpSession httpSession;
    String user;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public UsernameLogin(String user, HttpSession httpSession) {
        this.user = user;
        this.httpSession = httpSession;
    }

    @Override
    public boolean process() {
        Long userId;
        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();

        Criteria criteria = session.createCriteria(User.class);
        User userEntity = (User)criteria.add(Restrictions.eq("name", user)).uniqueResult();

        if (userEntity == null) {
            throw new UserException("User not found");
        } else {
            try {
                userId = (Long) httpSession.getAttribute("userId");
                if (userId == null) {
                    userId = userEntity.getUserId();
                    httpSession.setAttribute("userId", userId);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new UserException(e.getMessage());
            }
        }

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