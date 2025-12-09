package com.yaloys.user;

import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/")
public class UserResource {

    @Inject
    Template index;

    @Inject
    Template home;

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance index() {
        if (securityIdentity.isAnonymous()) {
            return index.instance();
        }
        return home.data("user", getCurrentUser());
    }

    @GET
    @Path("/home")
    @Authenticated
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance homePage() {
        return home.data("user", getCurrentUser());
    }

    @GET
    @Path("/products")
    @Authenticated
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance products() {
        return home.data("user", getCurrentUser());
    }

    @GET
    @Path("/orders")
    @Authenticated
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance orders() {
        return home.data("user", getCurrentUser());
    }

    @GET
    @Path("/reviews")
    @Authenticated
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance reviews() {
        return home.data("user", getCurrentUser());
    }

    private Map<String, String> getCurrentUser() {
        Map<String, String> user = new HashMap<>();
        user.put("name", securityIdentity.getPrincipal().getName());
        user.put("email", securityIdentity.getPrincipal().getName());
        user.put("role", securityIdentity.getRoles().toString());
        return user;
    }
}