package com.yaloys.reviews.controllers;

import com.yaloys.reviews.models.Review;
import com.yaloys.reviews.repositories.ReviewRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/reviews")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReviewResource {

    @Inject
    ReviewRepository reviewRepository;

    @GET
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getReviewById(@PathParam("id") Integer id) {
        Optional<Review> review = reviewRepository.findById(id);

        if (review.isPresent()) {
            return Response.ok(review.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/product/{productId}")
    public List<Review> getReviewsByProductId(@PathParam("productId") Integer productId) {
        return reviewRepository.findByProductId(productId);
    }

    @POST
    public Response createReview(Review review) {
        Review savedReview = reviewRepository.save(review);
        return Response.status(Response.Status.CREATED).entity(savedReview).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateReview(@PathParam("id") Integer id, Review review) {
        Optional<Review> existingReview = reviewRepository.findById(id);

        if (existingReview.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        review.setReviewId(id);
        Review updatedReview = reviewRepository.save(review);
        return Response.ok(updatedReview).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteReview(@PathParam("id") Integer id) {
        Optional<Review> review = reviewRepository.findById(id);

        if (review.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        reviewRepository.deleteById(id);
        return Response.noContent().build();
    }
}