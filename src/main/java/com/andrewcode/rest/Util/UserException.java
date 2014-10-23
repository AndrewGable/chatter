package com.andrewcode.rest.Util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by andrew on 9/21/14.
 * LoginException.java
 */
public class UserException extends WebApplicationException {

    public UserException(String msg) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(msg)
                .type("text/plain").build());
    }

}
