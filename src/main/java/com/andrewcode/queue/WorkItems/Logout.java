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
 * Logout.java
 */
public class Logout implements WorkItem {

    boolean isProcessed = false;

    String response;
    HttpServletRequest req;

    SessionFactory sessionFactory = Utils.createSessionFactory();

    public Logout(HttpServletRequest req) {
        this.req = req;
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean process() {
        Gson gson = new GsonBuilder().create();
        try {
            if (req == null) {
                System.out.println("Null request in context");
            } else {
                HttpSession httpSession = req.getSession();
                httpSession.invalidate();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        response = gson.toJson("Logged out");

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