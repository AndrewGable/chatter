package com.andrewcode.queue.Models;

import com.andrewcode.queue.Utils.WorkItem;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by andrew on 10/23/14.
 * Worker.java
 */

public class Worker implements Runnable {

    private ConcurrentLinkedQueue<WorkItem> workQueue = null;
    public boolean bRunning = true;

    public Worker(ConcurrentLinkedQueue<WorkItem> queue) {
        workQueue = queue;
    }

    @Override
    public void run() {
        while (bRunning) {
            long size = workQueue.size();

            WorkItem item = workQueue.poll();
            if (item != null) {
                item.process();
            } else {
                try {
                    Thread.currentThread().sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void stop() {
        bRunning = false;
    }

}