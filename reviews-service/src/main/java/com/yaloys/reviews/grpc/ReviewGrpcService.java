package com.yaloys.reviews.grpc;

import com.jewelry.grpc.review.*;
import com.yaloys.reviews.models.Review;
import com.yaloys.reviews.repositories.ReviewRepository;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;
import java.util.List;

@GrpcService
public class ReviewGrpcService extends ReviewServiceGrpc.ReviewServiceImplBase {

    @Inject
    ReviewRepository reviewRepository;

    @Override
    public void getReviewsByProduct(ReviewRequest request, StreamObserver<ReviewsResponse> responseObserver) {
        List<Review> reviews = reviewRepository.findByProductId(request.getProductId());

        ReviewsResponse.Builder responseBuilder = ReviewsResponse.newBuilder();

        for (Review review : reviews) {
            ReviewResponse reviewResponse = ReviewResponse.newBuilder().setId(review.getReviewId()).setProductId(review.getProductId())
                    .setRating(review.getRating()).setTitle(review.getTitle()).setBody(review.getBody())
                    .build();
            responseBuilder.addReviews(reviewResponse);
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}