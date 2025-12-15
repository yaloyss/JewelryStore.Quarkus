package com.yaloys.user.clients;

import com.yaloys.user.models.Order;
import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@Path("/api/orders")
@AccessToken
@RegisterRestClient(configKey = "order-service")
public interface OrderClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Order> getAllOrders();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Order getOrderById(@PathParam("id") Integer id);
}
