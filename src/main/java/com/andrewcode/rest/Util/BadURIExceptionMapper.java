package com.andrewcode.rest.Util;

import com.sun.jersey.api.NotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by andrew on 10/29/14.
 */
@Provider
public class BadURIExceptionMapper implements ExceptionMapper<NotFoundException> {

    public Response toResponse(NotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND). entity("404 - BAD URL"). build();
    }

}