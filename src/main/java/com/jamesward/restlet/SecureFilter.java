package com.jamesward.restlet;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.Form;
import org.restlet.data.Status;
import org.restlet.routing.Filter;
import org.restlet.routing.Redirector;

public class SecureFilter extends Filter {

    private boolean doRedirect;

    public SecureFilter(Context context, Restlet next) {
        super(context);
        doRedirect = false;
        setNext(next);
    }

    public SecureFilter(Context context, Restlet next, boolean doRedirect) {
        super(context);
        this.doRedirect = doRedirect;
        setNext(next);
    }

    public SecureFilter(Context context, Class<?> next) {
        super(context);
        doRedirect = false;
        setNext(next);
    }

    public SecureFilter(Context context, Class<?> next, boolean doRedirect) {
        super(context);
        this.doRedirect = doRedirect;
        setNext(next);
    }

    public boolean isDoRedirect() {
        return doRedirect;
    }

    public void setDoRedirect(boolean doRedirect) {
        this.doRedirect = doRedirect;
    }

    @Override
    protected int beforeHandle(Request request, Response response) {
        Form requestHeaders = (Form) request.getAttributes().get("org.restlet.http.headers");

        if ((requestHeaders.getValues("x-forwarded-proto") != null) && (requestHeaders.getValues("x-forwarded-proto").indexOf("https") != 0)) {
            if (doRedirect) {
                String target = "https://" + request.getHostRef().getHostDomain() + request.getResourceRef().getPath();
                Redirector redirector = new Redirector(getContext(), target, Redirector.MODE_CLIENT_SEE_OTHER);
                redirector.handle(request, response);
                return STOP;
            }
            else {
                response.setStatus(Status.CLIENT_ERROR_FORBIDDEN);
                return STOP;
            }
        }

        return CONTINUE;
    }

}