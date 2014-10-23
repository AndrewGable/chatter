package com.andrewcode.rest.Util;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by andrew on 9/22/14.
 */
public class FriendException extends WebApplicationException {

    public FriendException(String msg) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(msg)
                .type("text/plain").build());
    }

}
