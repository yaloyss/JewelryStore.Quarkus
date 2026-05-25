package com.yaloys.reviews.controllers;

import com.yaloys.reviews.models.Review;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@Authenticated
@PermitAll

public class ReviewResource {

    @GET
    public List<Review> getAllReviews() {
        return Review.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getReviewById(@PathParam("id") Integer id) {
        Review review = Review.findByReviewId(id);

        if (review != null) {
            return Response.ok(review).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/product/{productId}")
    public List<Review> getReviewsByProductId(@PathParam("productId") Integer productId) {
        return Review.findByProductId(productId);
    }

    @POST
    @Transactional
    public Response createReview(Review review) {
        review.persist();
        return Response.status(Response.Status.CREATED).entity(review).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateReview(@PathParam("id") Integer id, Review updatedReview) {
        Review review = Review.findByReviewId(id);

        if (review == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        review.productId = updatedReview.productId;
        review.rating = updatedReview.rating;
        review.title = updatedReview.title;
        review.body = updatedReview.body;
        return Response.ok(review).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteReview(@PathParam("id") Integer id) {
        Review review = Review.findByReviewId(id);

        if (review == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        review.delete();
        return Response.noContent().build();
    }
}