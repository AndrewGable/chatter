package com.andrewcode.rest.Models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by andrew on 10/29/14.
 */
@Entity
public class Queue {
    private int id;
    private long time;
    private String task;
    private Date date;

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
    @Column(name = "date", nullable = false, insertable = true, updatable = true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Queue queue = (Queue) o;

        if (date != queue.date) return false;
        if (id != queue.id) return false;
        if (time != queue.time) return false;
        if (task != null ? !task.equals(queue.task) : queue.task != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (int) (time ^ (time >>> 32));
        result = 31 * result + (task != null ? task.hashCode() : 0);
        return result;
    }
}
