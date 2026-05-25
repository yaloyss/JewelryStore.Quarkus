package com.yaloys.user.api;

import com.yaloys.user.clients.DiscussionClient;
import com.yaloys.user.models.Discussion;
import com.yaloys.user.models.Message;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;
import java.util.logging.Logger;

@Path("/api/discussions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class DiscussionsApiResource {

    private static final Logger LOG = Logger.getLogger(DiscussionsApiResource.class.getName());

    @Inject
    @RestClient
    DiscussionClient discussionClient;

    @GET
    public List<Discussion> list() {
        return discussionClient.getAllDiscussions();
    }

    @GET
    @Path("/{id}")
    public Discussion get(@PathParam("id") Integer id) {
        return discussionClient.getDiscussionById(id);
    }

    @GET
    @Path("/review/{reviewId}")
    public Response byReview(@PathParam("reviewId") Integer reviewId) {
        try {
            Discussion discussion = discussionClient.getDiscussionByReviewId(reviewId);
            return Response.ok(discussion).build();
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("404")) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            LOG.warning("Discussion by review failed: " + e.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    public Response create(Discussion discussion) {
        try {
            Discussion created = discussionClient.createDiscussion(discussion);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            LOG.warning("Create discussion failed: " + e.getMessage());
            return Response.serverError().entity(new ErrorBody(e.getMessage())).build();
        }
    }

    @POST
    @Path("/{id}/messages")
    public Response addMessage(@PathParam("id") Integer id, Message message) {
        try {
            Message created = discussionClient.addMessage(id, message);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            LOG.warning("Add message failed: " + e.getMessage());
            return Response.serverError().entity(new ErrorBody(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            discussionClient.deleteDiscussion(id);
            return Response.noContent().build();
        } catch (Exception e) {
            LOG.warning("Delete discussion failed: " + e.getMessage());
            return Response.serverError().entity(new ErrorBody(e.getMessage())).build();
        }
    }

    record ErrorBody(String error) {}
}
