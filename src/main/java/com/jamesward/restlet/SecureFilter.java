package com.jamesward.restlet;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.routing.Filter;
import org.restlet.routing.Redirector;

public class SecureFilter extends Filter {

    private boolean doRediect;

    public SecureFilter(Restlet next) {
        super();
        doRediect = false;
        setNext(next);
    }

    public SecureFilter(Restlet next, boolean doRedirect) {
        super();
        setNext(next);
    }

    public SecureFilter(Class<?> next) {
        super();
        doRediect = false;
        setNext(next);
    }

    public SecureFilter(Class<?> next, boolean doRedirect) {
        super();
        setNext(next);
    }

    public boolean isDoRediect() {
        return doRediect;
    }

    public void setDoRediect(boolean doRediect) {
        this.doRediect = doRediect;
    }

    @Override
    protected int beforeHandle(Request request, Response response) {
        Form requestHeaders = (Form) request.getAttributes().get("org.restlet.http.headers");

        if ((requestHeaders.getValues("x-forwarded-proto") != null) && (requestHeaders.getValues("x-forwarded-proto").indexOf("https") != 0)) {
            if (doRediect) {
                String target = "https://" + request.getHostRef().getHostDomain() + request.getResourceRef().getPath();
                System.out.println("target = " + target);
                Redirector redirector = new Redirector(getContext(), target, Redirector.MODE_CLIENT_PERMANENT);
                setNext(redirector);
                return CONTINUE;
            }
            else {
                response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
                return STOP;
            }
        }

        return CONTINUE;
    }

}