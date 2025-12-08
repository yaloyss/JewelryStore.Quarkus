package com.yaloys.orders.controllers;

import com.yaloys.orders.models.Order;
import com.yaloys.orders.repositories.OrderRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderRepository orderRepository;

    @GET
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") Integer id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isPresent()) {
            return Response.ok(order.get()).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(Order order) {
        Order savedOrder = orderRepository.save(order);
        return Response.status(Response.Status.CREATED).entity(savedOrder).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("id") Integer id, Order order) {
        Optional<Order> existingOrder = orderRepository.findById(id);

        if (existingOrder.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        order.setOrderId(id);
        Order updatedOrder = orderRepository.save(order);
        return Response.ok(updatedOrder).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@PathParam("id") Integer id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        orderRepository.delete(id);
        return Response.noContent().build();
    }
}