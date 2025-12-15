package com.yaloys.user.clients;

import com.yaloys.user.models.Review;
import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@Path("/api/reviews")
@AccessToken
@RegisterRestClient(configKey = "review-service")
public interface ReviewClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Review> getAllReviews();

    @GET
    @Path("/product/{productId}")
    @Produces(MediaType.APPLICATION_JSON)
    List<Review> getReviewsByProductId(@PathParam("productId") Integer productId);
}