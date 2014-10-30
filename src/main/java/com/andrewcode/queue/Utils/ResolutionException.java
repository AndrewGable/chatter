package com.andrewcode.queue.Utils;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by andrew on 10/29/14.
 */
public class ResolutionException extends WebApplicationException {

    public ResolutionException(String msg) {
        super(Response.status(Response.Status.BAD_REQUEST).entity(msg)
                .type("text/plain").build());
    }

}