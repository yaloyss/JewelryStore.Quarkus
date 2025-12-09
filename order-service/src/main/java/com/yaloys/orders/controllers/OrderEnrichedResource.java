package com.yaloys.orders.controllers;

import com.yaloys.orders.client.ProductClient;
import com.yaloys.orders.models.Order;
import com.yaloys.orders.models.OrderItem;
import com.yaloys.orders.models.ProductO;
import com.yaloys.orders.repositories.OrderRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Path("/api/orders-enriched")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderEnrichedResource {

    @Inject
    OrderRepository orderRepository;

    @Inject
    @RestClient
    ProductClient productClient;

    @GET
    @Path("/{id}")
    public Response getOrderWithProducts(@PathParam("id") Integer id) {
        Optional<Order> orderOpt = orderRepository.findById(id);

        if (orderOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Order order = orderOpt.get();
        Map<String, Object> enrichedOrder = new HashMap<>();
        enrichedOrder.put("order", order);
        Map<Integer, ProductO> products = new HashMap<>();
        for (OrderItem item : order.getOrderItems()) {
            try
            {
                ProductO product = productClient.getProductById(item.getProductId());
                products.put(item.getProductId(), product);
            }
            catch (Exception e)
            {
                System.out.println("Could not fetch product: " + item.getProductId());
            }
        }
        enrichedOrder.put("products", products);
        return Response.ok(enrichedOrder).build();
    }
}