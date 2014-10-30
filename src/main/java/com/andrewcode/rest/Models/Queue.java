package com.andrewcode.rest.Models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by andrew on 10/30/14.
 */
@Entity
public class Queue {
    private int id;
    private long time;
    private String task;
    private Date date;
    private String serverName;

    @Id
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "time", nullable = false, insertable = true, updatable = true)
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Basic
    @Column(name = "task", nullable = false, insertable = true, updatable = true, length = 256)
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    @Basic
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = true, insertable = true, updatable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Basic
    @Column(name = "server_name", nullable = true, insertable = true, updatable = true, length = 256)
    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Queue queue = (Queue) o;

        if (id != queue.id) return false;
        if (time != queue.time) return false;
        if (date != null ? !date.equals(queue.date) : queue.date != null) return false;
        if (serverName != null ? !serverName.equals(queue.serverName) : queue.serverName != null) return false;
        if (task != null ? !task.equals(queue.task) : queue.task != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (task != null ? task.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
        return result;
    }
}
