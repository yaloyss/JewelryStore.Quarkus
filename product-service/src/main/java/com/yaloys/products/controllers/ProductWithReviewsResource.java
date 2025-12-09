package com.yaloys.products.controllers;

import com.yaloys.products.client.ReviewClient;
import com.yaloys.products.models.Product;
import com.yaloys.reviews.models.Review;
import com.yaloys.products.repositories.ProductRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/api/products-with-reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductWithReviewsResource {

    @Inject
    ProductRepository productRepository;

    @Inject
    @RestClient
    ReviewClient reviewClient;

    @GET
    @Path("/{id}")
    public Response getProductWithReviews(@PathParam("id") Integer id) {
        Optional<Product> productOpt = Optional.ofNullable(productRepository.findById(id));

        if (productOpt.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Product product = productOpt.get();
        Map<String, Object> enrichedProduct = new HashMap<>();
        enrichedProduct.put("product", product);

        try {
            List<Review> reviews = reviewClient.getReviewsByProductId(id);
            enrichedProduct.put("reviews", reviews);
        }
        catch (Exception e) {
            System.out.println("Could not fetch reviews for product: " + id);
            enrichedProduct.put("reviews", List.of());
        }
        return Response.ok(enrichedProduct).build();
    }
}