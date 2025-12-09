package com.yaloys.products.client;

import com.yaloys.reviews.models.Review;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/api/reviews")
@RegisterRestClient(configKey = "review-service")
public interface ReviewClient {

    @GET
    @Path("/product/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Review> getReviewsByProductId(@PathParam("productId") Integer productId);
}