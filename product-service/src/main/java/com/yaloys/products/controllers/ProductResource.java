package com.yaloys.products.controllers;

import com.yaloys.products.messaging.ProductEventPublisher;
import com.yaloys.products.models.Product;
import com.yaloys.products.repositories.ProductRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import jakarta.transaction.Transactional;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @Inject
    ProductEventPublisher eventPublisher;

    @GET
    public List<Product> getAllProducts() {
        return productRepository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Integer id) {
        return productRepository.findByIdOptional(Long.valueOf(id)).map(product -> Response.ok(product).build()).orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @GET
    @Path("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathParam("categoryId") Integer categoryId) {
        return productRepository.findByCategory(categoryId);
    }

    @GET
    @Path("/metal/{metalId}")
    public List<Product> getProductsByMetal(@PathParam("metalId") Integer metalId) {
        return productRepository.findByMetal(metalId);
    }

    @GET
    @Path("/manufacturer/{manufacturer}")
    public List<Product> getProductsByManufacturer(@PathParam("manufacturer") String manufacturer) {
        return productRepository.findByManufacturer(manufacturer);
    }

//    @POST
//    @Transactional
//    public Response createProduct(Product product) {
//        productRepository.persist(product);
//        return Response.status(Response.Status.CREATED).entity(product).build();
//    }
    @POST
    @Transactional
    public Response createProduct(Product product) {
        productRepository.persist(product); //saving product

        // publishing event
        eventPublisher.publishProductCreated(product.getProductId(), product.getName());
        return Response.status(Response.Status.CREATED).entity(product).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateProduct(@PathParam("id") Integer id, Product product) {

        Product existing = productRepository.findById(Long.valueOf(id));
        if (existing == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        BigDecimal oldPrice = existing.getPrice();
        BigDecimal newPrice = product.getPrice();
        existing.setName(product.getName());
        existing.setPrice(newPrice);
        existing.setCategoryId(product.getCategoryId());
        existing.setMetalId(product.getMetalId());
        existing.setManufacturer(product.getManufacturer());
        existing.setStoneId(product.getStoneId());

        if (oldPrice != null && newPrice != null && oldPrice.compareTo(newPrice) != 0) {
            eventPublisher.publishPriceChanged(existing.getProductId(), existing.getName(), oldPrice, newPrice);
        } else {
            eventPublisher.publishProductUpdated(existing.getProductId(), existing.getName());
        }
        return Response.ok(existing).build();
    }


    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteProduct(@PathParam("id") Integer id) {
        productRepository.deleteById(Long.valueOf(id));
        return Response.noContent().build();
    }
}