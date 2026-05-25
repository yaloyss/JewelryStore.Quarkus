package com.yaloys.user.api;

import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

@Path("/api/me")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class MeApiResource {

    @Inject
    SecurityIdentity securityIdentity;

    @GET
    public Map<String, Object> me() {
        Map<String, Object> user = new HashMap<>();
        if (securityIdentity.isAnonymous()) {
            user.put("anonymous", true);
            user.put("name", "Guest");
            user.put("email", null);
            user.put("roles", java.util.List.of());
        } else {
            user.put("anonymous", false);
            user.put("name", securityIdentity.getPrincipal().getName());
            user.put("email", securityIdentity.getPrincipal().getName());
            user.put("roles", securityIdentity.getRoles());
        }
        return user;
    }
}
