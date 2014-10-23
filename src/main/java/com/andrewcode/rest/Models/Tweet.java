package com.andrewcode.rest.Models;

import javax.persistence.*;

/**
 * Created by andrew on 9/19/14.
 */
@Entity
public class Tweet {
    private long tweetId;
    private long userId;
    private String message;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "tweet_id", nullable = false, insertable = true, updatable = true)
    public long getTweetId() {
        return tweetId;
    }

    public void setTweetId(long tweetId) {
        this.tweetId = tweetId;
    }

    @Basic
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "message", nullable = false, insertable = true, updatable = true, length = 128)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tweet tweet = (Tweet) o;

        if (tweetId != tweet.tweetId) return false;
        if (userId != tweet.userId) return false;
        if (message != null ? !message.equals(tweet.message) : tweet.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (tweetId ^ (tweetId >>> 32));
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
