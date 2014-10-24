package com.andrewcode.queue.Utils;

import javax.servlet.ServletContextEvent;
import java.util.Properties;

/**
 * Created by andrew on 10/24/14.
 * ServletContextListener.java
 */
public class ServletContextListener implements
        javax.servlet.ServletContextListener {

    Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // Stop the thread factory
        ProcessingFactory.destroy("processing-queue");
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        // Read the properties from here
        properties.put("workers", event.getServletContext().getInitParameter("workers"));
        properties.put("queueDepth",event.getServletContext().getInitParameter("queueDepth"));

        // Start the queue and the thread factory here
        ProcessingFactory.create("processing-queue", properties);
    }

}
