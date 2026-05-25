package com.yaloys.user.clients;

import com.yaloys.user.models.Review;
import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@Path("/api/reviews")
//@AccessToken
@RegisterRestClient(configKey = "review-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface ReviewClient {

    @GET
    List<Review> getAllReviews();

    @GET
    @Path("/{id}")
    Review getReviewById(@PathParam("id") Integer id);

    @GET
    @Path("/product/{productId}")
    List<Review> getReviewsByProductId(@PathParam("productId") Integer productId);

    @POST
    Review createReview(Review review);

    @PUT
    @Path("/{id}")
    Review updateReview(@PathParam("id") Integer id, Review review);

    @DELETE
    @Path("/{id}")
    void deleteReview(@PathParam("id") Integer id);
}