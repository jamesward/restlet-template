package com.jamesward.restlet;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloResource extends ServerResource {

    @Get
    public String represent() {
        return "hello, world";
    }

}

