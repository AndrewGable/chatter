package com.andrewcode.queue.Utils;

import java.net.UnknownHostException;

/**
 * Created by andrew on 10/23/14.
 * WorkItem.java
 */
public interface WorkItem {
    public boolean process() throws UnknownHostException;
}
