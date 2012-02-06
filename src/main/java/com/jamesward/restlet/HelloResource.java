package com.jamesward.restlet;

import org.restlet.data.Form;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

public class HelloResource extends ServerResource {

    @Get
    public String represent() {
        Form requestHeaders = (Form) this.getRequest().getAttributes().get("org.restlet.http.headers");

        boolean secure = false;
        if (requestHeaders.getValues("x-forwarded-proto") != null) {
            secure = requestHeaders.getValues("x-forwarded-proto").contains("https");
        }

        System.out.println("secure = " + secure);

        return "hello, world";
    }

}