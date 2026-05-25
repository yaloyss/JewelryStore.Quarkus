package com.yaloys.user.api;

import com.yaloys.user.clients.OrderClient;
import com.yaloys.user.models.Order;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class OrdersApiResource {

    @Inject
    @RestClient
    OrderClient orderClient;

    @GET
    public List<Order> list() {
        return orderClient.getAllOrders();
    }

    @GET
    @Path("/{id}")
    public Order get(@PathParam("id") Integer id) {
        return orderClient.getOrderById(id);
    }
}
