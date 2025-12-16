package com.yaloys.orders.client;

import com.yaloys.orders.models.ProductO;
import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/products")
@AccessToken
@RegisterRestClient(configKey = "product-service")
public interface ProductClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    ProductO getProductById(@PathParam("id") Integer id);
}