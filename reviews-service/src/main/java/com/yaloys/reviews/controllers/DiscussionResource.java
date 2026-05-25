package com.yaloys.reviews.controllers;

import com.yaloys.reviews.models.Discussion;
import com.yaloys.reviews.models.Message;
import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/discussions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
//@Authenticated
@PermitAll
public class DiscussionResource {

    @GET
    public List<Discussion> getAllDiscussions() {
        return Discussion.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getDiscussionById(@PathParam("id") Integer id) {
        Discussion discussion = Discussion.findByDiscussionId(id);

        if (discussion != null) {
            return Response.ok(discussion).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/review/{reviewId}")
    public Response getDiscussionByReviewId(@PathParam("reviewId") Integer reviewId) {
        Discussion discussion = Discussion.findByReviewId(reviewId);

        if (discussion != null) {
            return Response.ok(discussion).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Transactional
    public Response createDiscussion(Discussion discussion) {
        discussion.persist();
        return Response.status(Response.Status.CREATED).entity(discussion).build();
    }

    @POST
    @Path("/{id}/messages")
    @Transactional
    public Response addMessage(@PathParam("id") Integer discussionId, Message message) {
        Discussion discussion = Discussion.findByDiscussionId(discussionId);

        if (discussion == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        message.discussion = discussion;
        message.persist();
        return Response.status(Response.Status.CREATED).entity(message).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteDiscussion(@PathParam("id") Integer id) {
        Discussion discussion = Discussion.findByDiscussionId(id);

        if (discussion == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        discussion.delete();
        return Response.noContent().build();
    }
}