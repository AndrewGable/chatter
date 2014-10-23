package com.andrewcode.rest.Controllers;

import com.andrewcode.rest.Models.User;
import com.andrewcode.rest.Util.UserException;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by andrew on 9/17/14.
 * LoginService.java
 */
@Path("/users")
public class UserService {
    SessionFactory sessionFactory = Utils.createSessionFactory();

    @POST
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUser(@FormParam("userId") Long userId,
                            @Context HttpServletRequest req) throws UserException {
        Long sessionUserId;
        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();

        User userEntity = (User) session.get(User.class, userId);

        if (userEntity == null) {
            throw new UserException("User not found");
        } else {
            try {
                if (req == null) {
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
                throw new UserException(e.getMessage());
            }
        }

        return gson.toJson("Login Successful");
    }

    @POST
    @Path("/login/username")
    @Produces(MediaType.APPLICATION_JSON)
    public String loginUsername(@FormParam("username") String user,
                            @Context HttpServletRequest req) throws UserException {
        Long userId;
        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();

        Criteria criteria = session.createCriteria(User.class);
        User userEntity = (User)criteria.add(Restrictions.eq("name", user)).uniqueResult();

        if (userEntity == null) {
            throw new UserException("User not found");
        } else {
            try {
                if (req == null) {
                    throw new UserException("Null request in context");
                } else {
                    HttpSession httpSession = req.getSession();
                    userId = (Long) httpSession.getAttribute("userId");
                    if (userId == null) {
                        userId = userEntity.getUserId();
                        httpSession.setAttribute("userId", userId);
                    }
                }
            } catch (Exception e) {
                throw new UserException(e.getMessage());
            }
        }

        return gson.toJson("Login Successful");
    }

    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    public String createUser(@FormParam("username") String user,
                                @FormParam("email") String email) throws UserException {

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

        return gson.toJson(userId);
    }

    @GET
    @Path("/{username}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getUsername(@PathParam("username") String username){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Gson gson = new GsonBuilder().create();
        Long userId;

        User existingUser = (User) session.createQuery("from User WHERE name= :name ").setParameter("name", username).uniqueResult();

        if (existingUser == null) {
            throw new UserException("User doesn't exist with that username");
        } else {
            userId = existingUser.getUserId();
        }

        transaction.commit();
        session.close();

        return gson.toJson(userId);
    }

    @POST
    @Path("/logout")
    public String logout(@Context HttpServletRequest req){
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

        return gson.toJson("Logged out");
    }

}
