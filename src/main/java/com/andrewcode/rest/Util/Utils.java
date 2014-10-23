package com.andrewcode.rest.Util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by andrew on 9/20/14.
 * Utils.java
 */
public class Utils {

    public static Long getUserId(HttpServletRequest req){
        Long userId = null;
        try {
            if (req == null) {
                System.out.println("Null request in context");
            } else {
                HttpSession httpSession = req.getSession();
                userId = (Long) httpSession.getAttribute("userId");
                if (userId == null) {
                    throw new UserException("User's session is null.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return userId;
    }

    public static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

}
