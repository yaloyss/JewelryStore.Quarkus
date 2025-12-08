package com.yaloys.products.controllers;

import com.yaloys.products.models.Product;
import com.yaloys.products.repositories.ProductRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @GET
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getProductById(@PathParam("id") Integer id) {
        Optional<Product> product = Optional.ofNullable(productRepository.findById(id));

        if (product.isPresent()) {
            return Response.ok(product.get()).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }
    
    @GET
    @Path("/category/{categoryId}")
    public List<Product> getProductsByCategory(@PathParam("categoryId") Integer categoryId) {
        return productRepository.findByCategory(categoryId);
    }

    @POST
    public Response createProduct(Product product) {
        Product saved = productRepository.save(product);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") Integer id, Product product) {
        if (!productRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        product.setProductId(id);
        Product updated = productRepository.save(product);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Integer id) {
        if (!productRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        productRepository.deleteById(id);
        return Response.noContent().build();
    }

    @POST
    @Path("/{productId}/stones/{stoneId}")
    public Response addStoneToProduct(@PathParam("productId") Integer productId,
                                      @PathParam("stoneId") Integer stoneId) {
        if (!productRepository.existsById(productId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        productRepository.addStoneToProduct(productId, stoneId);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{productId}/stones/{stoneId}")
    public Response removeStoneFromProduct(@PathParam("productId") Integer productId,
                                           @PathParam("stoneId") Integer stoneId) {
        if (!productRepository.existsById(productId)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        productRepository.removeStoneFromProduct(productId, stoneId);
        return Response.ok().build();
    }
}