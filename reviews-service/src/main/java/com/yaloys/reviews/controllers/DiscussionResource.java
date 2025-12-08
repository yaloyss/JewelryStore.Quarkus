package com.yaloys.reviews.controllers;

import com.yaloys.reviews.models.Discussion;
import com.yaloys.reviews.models.Message;
import com.yaloys.reviews.repositories.DiscussionRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/api/discussions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DiscussionResource {

    @Inject
    DiscussionRepository discussionRepository;

    @GET
    public List<Discussion> getAllDiscussions() {
        return discussionRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getDiscussionById(@PathParam("id") Integer id) {
        Optional<Discussion> discussion = discussionRepository.findById(id);

        if (discussion.isPresent()) {
            return Response.ok(discussion.get()).build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/review/{reviewId}")
    public Response getDiscussionByReviewId(@PathParam("reviewId") Integer reviewId) {
        Optional<Discussion> discussion = discussionRepository.findByReviewId(reviewId);

        if (discussion.isPresent()) {
            return Response.ok(discussion.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    public Response createDiscussion(Discussion discussion) {
        Discussion savedDiscussion = discussionRepository.save(discussion);
        return Response.status(Response.Status.CREATED).entity(savedDiscussion).build();
    }

    @POST
    @Path("/{id}/messages")
    public Response addMessage(@PathParam("id") Integer discussionId, Message message) {
        Message savedMessage = discussionRepository.addMessage(discussionId, message);

        if (savedMessage != null) {
            return Response.status(Response.Status.CREATED).entity(savedMessage).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteDiscussion(@PathParam("id") Integer id) {
        Optional<Discussion> discussion = discussionRepository.findById(id);

        if (discussion.isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        discussionRepository.deleteById(id);
        return Response.noContent().build();
    }
}
