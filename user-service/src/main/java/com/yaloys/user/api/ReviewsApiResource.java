package com.yaloys.user.api;

import com.yaloys.user.clients.ReviewClient;
import com.yaloys.user.models.Review;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;
import java.util.logging.Logger;

@Path("/api/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class ReviewsApiResource {

    private static final Logger LOG = Logger.getLogger(ReviewsApiResource.class.getName());

    @Inject
    @RestClient
    ReviewClient reviewClient;

    @GET
    public List<Review> list() {
        return reviewClient.getAllReviews();
    }

    @GET
    @Path("/{id}")
    public Review get(@PathParam("id") Integer id) {
        return reviewClient.getReviewById(id);
    }

    @GET
    @Path("/product/{productId}")
    public List<Review> byProduct(@PathParam("productId") Integer productId) {
        return reviewClient.getReviewsByProductId(productId);
    }

    @POST
    public Response create(Review review) {
        try {
            Review created = reviewClient.createReview(review);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            LOG.warning("Create review failed: " + e.getMessage());
            return Response.serverError().entity(new ErrorBody(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Review review) {
        try {
            Review updated = reviewClient.updateReview(id, review);
            return Response.ok(updated).build();
        } catch (Exception e) {
            LOG.warning("Update review failed: " + e.getMessage());
            return Response.serverError().entity(new ErrorBody(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            reviewClient.deleteReview(id);
            return Response.noContent().build();
        } catch (Exception e) {
            LOG.warning("Delete review failed: " + e.getMessage());
            return Response.serverError().entity(new ErrorBody(e.getMessage())).build();
        }
    }

    record ErrorBody(String error) {}
}
