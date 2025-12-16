package com.yaloys.orders.controllers;

import com.yaloys.orders.models.Order;
import com.yaloys.orders.repositories.OrderRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jakarta.transaction.Transactional;

@Path("/api/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    OrderRepository orderRepository;

    @GET
    public List<Order> getAllOrders() {
        return orderRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") Integer id) {
        return orderRepository.findByIdOptional(Long.valueOf(id))
                .map(order -> Response.ok(order).build()).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/customer/{customerId}")
    public List<Order> getOrdersByCustomerId(@PathParam("customerId") Integer customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    @GET
    @Path("/status/{status}")
    public List<Order> getOrdersByStatus(@PathParam("status") String status) {
        return orderRepository.findByStatus(status);
    }

    @POST
    @Transactional
    public Response createOrder(Order order) {
        orderRepository.persist(order);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateOrder(@PathParam("id") Integer id, Order order) {
        Order existingOrder = orderRepository.findById(Long.valueOf(id));

        if (existingOrder == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        existingOrder.setCustomerId(order.getCustomerId());
        existingOrder.setStatus(order.getStatus());
        existingOrder.setOrderDate(order.getOrderDate());
        return Response.ok(existingOrder).build();
    }

    @PATCH
    @Path("/{id}/status")
    @Transactional
    public Response updateOrderStatus(@PathParam("id") Integer id, @QueryParam("status") String status) {
        Order order = orderRepository.findById(Long.valueOf(id));

        if (order == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        order.setStatus(status);
        return Response.ok(order).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteOrder(@PathParam("id") Integer id) {
        boolean deleted = orderRepository.deleteById(Long.valueOf(id));

        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }
}