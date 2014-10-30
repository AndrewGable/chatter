package com.andrewcode.rest.Models;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by andrew on 10/30/14.
 */
@Entity
public class Errors {
    private int id;
    private Integer errorCode;
    private Date date;
    private String exception;
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
    @Column(name = "error_code", nullable = true, insertable = true, updatable = true)
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
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
    @Column(name = "exception", nullable = true, insertable = true, updatable = true, length = 256)
    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
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

        Errors errors = (Errors) o;

        if (id != errors.id) return false;
        if (date != null ? !date.equals(errors.date) : errors.date != null) return false;
        if (errorCode != null ? !errorCode.equals(errors.errorCode) : errors.errorCode != null) return false;
        if (exception != null ? !exception.equals(errors.exception) : errors.exception != null) return false;
        if (serverName != null ? !serverName.equals(errors.serverName) : errors.serverName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (errorCode != null ? errorCode.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (exception != null ? exception.hashCode() : 0);
        result = 31 * result + (serverName != null ? serverName.hashCode() : 0);
        return result;
    }
}
