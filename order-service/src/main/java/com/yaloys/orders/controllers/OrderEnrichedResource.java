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
import com.yaloys.orders.dtos.ProductDTO;
import org.jboss.logging.Logger;
import java.util.List;

@Path("/api/orders-enriched")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderEnrichedResource {

    private static final Logger LOG = Logger.getLogger(OrderEnrichedResource.class);

    @Inject
    OrderRepository orderRepository;

    @Inject
    @RestClient
    ProductClient productClient;

    @GET
    @Path("/{id}")
    public Response getOrderWithProducts(@PathParam("id") Integer id) {
        return orderRepository.findByIdOptional(Long.valueOf(id))
                .map(this::enrichOrderWithProducts).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    public Response getAllOrdersEnriched() {
        List<Order> orders = orderRepository.listAll();
        List<Map<String, Object>> enrichedOrders = orders.stream().map(this::buildEnrichedOrderMap).toList();

        return Response.ok(enrichedOrders).build();
    }

    @GET
    @Path("/customer/{customerId}")
    public Response getCustomerOrdersEnriched(@PathParam("customerId") Integer customerId) {
        List<Order> orders = orderRepository.findByCustomerId(customerId);
        List<Map<String, Object>> enrichedOrders = orders.stream().map(this::buildEnrichedOrderMap).toList();

        return Response.ok(enrichedOrders).build();
    }

    private Response enrichOrderWithProducts(Order order) {
        Map<String, Object> enrichedOrder = buildEnrichedOrderMap(order);
        return Response.ok(enrichedOrder).build();
    }

    private Map<String, Object> buildEnrichedOrderMap(Order order) {
        Map<String, Object> enrichedOrder = new HashMap<>();
        enrichedOrder.put("order", order);

        Map<Integer, ProductDTO> products = new HashMap<>();
        for (OrderItem item : order.getOrderItems()) {
            try {
                ProductO productO = productClient.getProductById(item.getProductId());
                ProductDTO productDTO = new ProductDTO(productO.getProductId(), productO.getName(), productO.getPrice().toString());
                products.put(item.getProductId(), productDTO);
            } catch (Exception e) {
                LOG.warnf("Could not fetch product %d: %s", item.getProductId(), e.getMessage());
            }
        }
        enrichedOrder.put("products", products);
        return enrichedOrder;
    }
}