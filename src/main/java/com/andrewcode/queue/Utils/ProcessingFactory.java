package com.andrewcode.queue.Utils;

import com.andrewcode.queue.Controllers.TaskQueue;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by andrew on 10/24/14.
 * ProcessingFactory.java
 */

public class ProcessingFactory {

    static ConcurrentHashMap<String, TaskQueue> map = new ConcurrentHashMap<String, TaskQueue>();

    public static void destroy(String queueName) {
        if (map.containsKey(queueName)) {
            TaskQueue queue = map.remove(queueName);
            queue.destroy();
        }
    }

    public static void create(String queueName, Properties properties) {
        if (!map.containsKey(queueName)) {
            TaskQueue queue = TaskQueue.create(properties);
            map.putIfAbsent(queueName, queue);
        }
    }

    public static TaskQueue getTaskQueue(String queueName) {
        if (map.containsKey(queueName)) {
            return map.get(queueName);
        } else {
            return null;
        }
    }

}
