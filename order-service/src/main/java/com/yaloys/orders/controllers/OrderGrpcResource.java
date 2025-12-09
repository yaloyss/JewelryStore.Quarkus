package com.yaloys.orders.controllers;

import com.yaloys.orders.models.Order;
import com.yaloys.orders.models.OrderItem;
import com.yaloys.orders.repositories.OrderRepository;
import com.yaloys.products.grpc.product.ProductServiceGrpc;
import com.yaloys.products.grpc.product.ProductsRequest;
import com.yaloys.products.grpc.product.ProductsResponse;
import com.yaloys.orders.dtos.ProductDTO;
import io.quarkus.grpc.GrpcClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.*;
import java.util.stream.Collectors;

@Path("/api/orders-grpc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderGrpcResource {

    @Inject
    OrderRepository orderRepository;

    @GrpcClient("product-service")
    ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

    @GET
    @Path("/{id}")
    public Response getOrderWithProductsGrpc(@PathParam("id") Integer id) {
        Optional<Order> orderOpt = orderRepository.findById(id);

        if (orderOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Order order = orderOpt.get();
        Map<String, Object> enrichedOrder = new HashMap<>();
        enrichedOrder.put("order", order);
        //getting ids from order items
        List<Integer> productIds = order.getOrderItems().stream().map(OrderItem::getProductId).collect(Collectors.toList());

        try {
            ProductsRequest request = ProductsRequest.newBuilder().addAllIds(productIds).build();
            ProductsResponse response = productServiceStub.getProducts(request);
            List<ProductDTO> productsDto = response.getProductsList().stream().map(ProductDTO::new).collect(Collectors.toList());
            enrichedOrder.put("products", productsDto);
        }
        catch (Exception e) {
            System.out.println("gRPC call failed: " + e.getMessage());
            enrichedOrder.put("products", List.of());
        }
        return Response.ok(enrichedOrder).build();
    }
}
