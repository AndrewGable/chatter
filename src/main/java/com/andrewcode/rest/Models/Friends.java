package com.andrewcode.rest.Models;

import javax.persistence.*;

/**
 * Created by andrew on 9/19/14.
 */
@Entity
public class Friends {
    private long userId;
    private long friendsId;
    private String accepted;
    private long friendshipId;

    @Basic
    @Column(name = "user_id", nullable = false, insertable = true, updatable = true)
    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Basic
    @Column(name = "friends_id", nullable = false, insertable = true, updatable = true)
    public long getFriendsId() {
        return friendsId;
    }

    public void setFriendsId(long friendsId) {
        this.friendsId = friendsId;
    }

    @Basic
    @Column(name = "accepted", nullable = false, insertable = true, updatable = true, length = 1)
    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "friendship_id", nullable = false, insertable = true, updatable = true)
    public long getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(long friendshipId) {
        this.friendshipId = friendshipId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Friends friends = (Friends) o;

        if (friendsId != friends.friendsId) return false;
        if (friendshipId != friends.friendshipId) return false;
        if (userId != friends.userId) return false;
        if (accepted != null ? !accepted.equals(friends.accepted) : friends.accepted != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (friendsId ^ (friendsId >>> 32));
        result = 31 * result + (accepted != null ? accepted.hashCode() : 0);
        result = 31 * result + (int) (friendshipId ^ (friendshipId >>> 32));
        return result;
    }
}
