package com.andrewcode.queue.Controllers;

import com.andrewcode.queue.Utils.ProcessingFactory;
import com.andrewcode.queue.Utils.ResolutionException;
import com.andrewcode.rest.Models.Errors;
import com.andrewcode.rest.Models.Queue;
import com.andrewcode.rest.Models.User;
import com.andrewcode.rest.Util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.util.*;

/**
 * Created by andrew on 10/23/14.
 * MonitorService.java
 */

@Path("/monitor")
public class MonitorService {

    SessionFactory sessionFactory = Utils.createSessionFactory();

    @GET
    @Path("/processingtime")
    @Produces(MediaType.APPLICATION_JSON)
    @SuppressWarnings("unchecked")
    public String getQueueProcessingTime() {
        int j = 0, tenths;
        long sum = 0;
        long[] averages = new long[10];

        Gson gson = new GsonBuilder().create();
        Session session = sessionFactory.openSession();

        // Get all the queue data in a list
        List<Queue> times = session.createCriteria(Queue.class).list();

        String ip = Utils.getServerName();

        for (int i = 0; i < times.size(); i++) {
            if(!times.get(i).getServerName().equals(ip)){
                times.remove(i);
            }
        }

        // Sort the data based on time
        Collections.sort(times, new Comparator<Queue>() {
            public int compare(Queue left, Queue right)  {
                Long l = left.getTime();
                Long r = right.getTime();
                return l.compareTo(r);
            }
        });

        // If times size is less than size of averages we get divide by zero
        if (times.size() <= 10){
            for (int i = 0; i < times.size(); i++) {
                averages[i] = times.get(i).getTime();
            }
        } else {
            // Divide the list size into tenths
            tenths = times.size() / 10;

            // Loop through the size of list
            for (int i = 0; i < times.size(); i++) {
                // Add each time and get a running sum
                sum += times.get(i).getTime();
                if ((i + 1) % tenths == 0 && j != averages.length) {
                    // If in a tenth value take the average
                    averages[j] = sum/tenths;
                    sum = 0;
                    j++;
                }
                // The last index, may need to average more than previous
                else if ((i + 1) % tenths == 0 && j == averages.length)
                {
                    averages[9] = sum/tenths;
                    sum = 0;
                }
            }
        }

        session.close();

        return gson.toJson(averages);
    }

    @GET
    @Path("/queuedepth")
    @Produces(MediaType.APPLICATION_JSON)
    public String getQueueDepth() {
        Gson gson = new GsonBuilder().create();
        long depth = ProcessingFactory.getTaskQueue("processing-queue").getDepth();
        return gson.toJson(depth);
    }

    @GET
    @Path("/qps/{resolution}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getQps(@PathParam("resolution") String resolution) throws ParseException {
        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();

        String ip = Utils.getServerName();

        // Minute
        if (resolution.equals("minutes")) {
            long MIN_IN_MS = 1000 * 60 * 100;
            Date startDate = new Date(System.currentTimeMillis() - (MIN_IN_MS));
            Criteria criteria = session.createCriteria(Queue.class)
                    .add(Restrictions.between("date", startDate, new Date()));
            criteria.add(Restrictions.eq("serverName", ip));
            String result = gson.toJson(criteria.list().size());
            session.close();
            return result;
        }
        // Hours
        else if (resolution.equals("hours")) {
            long HOUR_IN_MS = 1000 * 60 * 60 * 100;
            Date startDate = new Date(System.currentTimeMillis() - (HOUR_IN_MS));
            Criteria criteria = session.createCriteria(Queue.class)
                    .add(Restrictions.between("date", startDate, new Date()));
            criteria.add(Restrictions.eq("serverName", ip));
            String result = gson.toJson(criteria.list().size());
            session.close();
            return result;
        }
        // Days
        else if (resolution.equals("days")) {
            long DAYS_IN_MS = 100 * 24L * 60 * 60 * 1000;
            Date startDate = new Date(System.currentTimeMillis() - (DAYS_IN_MS));
            Criteria criteria = session.createCriteria(Queue.class)
                    .add(Restrictions.between("date", startDate, new Date()));
            criteria.add(Restrictions.eq("serverName", ip));
            String result = gson.toJson(criteria.list().size());
            session.close();
            return result;
        }
        // Months
        else if (resolution.equals("months")) {
            long MONTHS_IN_MS = 100 * 30 * 24L * 60 * 60 * 1000;
            Date startDate = new Date(System.currentTimeMillis() - (MONTHS_IN_MS));
            Criteria criteria = session.createCriteria(Queue.class)
                    .add(Restrictions.between("date", startDate, new Date()));
            criteria.add(Restrictions.eq("serverName", ip));
            String result = gson.toJson(criteria.list().size());
            session.close();
            return result;
        }
        // Throw an exception
        else {
            // Log the exception
            Transaction transaction = session.beginTransaction();
            Errors error = new Errors();
            error.setErrorCode(400);
            error.setDate(new Date());
            error.setException("ResolutionException");
            error.setServerName(Utils.getServerName());
            session.save(error);
            transaction.commit();
            session.close();

            throw new ResolutionException("The resolution must end in 'm' for minutes, 'h' for hours, 'd' for days " +
                    " or 'M' for months. Example: 10m for past messages processed in pass 10 minutes.");
        }
    }

    @GET
    @Path("/errors/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getErrors(@PathParam("type") int type) {
        Session session = sessionFactory.openSession();
        Gson gson = new GsonBuilder().create();

        List query = session.createQuery("from Errors WHERE errorCode= :errorCode and serverName= :serverName")
                .setParameter("errorCode", type).setParameter("serverName", Utils.getServerName()).list();


        String result = gson.toJson(query.size());
        session.close();

        return result;
    }


}
