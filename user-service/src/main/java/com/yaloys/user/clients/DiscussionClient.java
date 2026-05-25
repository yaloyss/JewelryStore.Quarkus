package com.yaloys.user.clients;

import com.yaloys.user.models.Discussion;
import com.yaloys.user.models.Message;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@Path("/api/discussions")
@RegisterRestClient(configKey = "review-service")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public interface DiscussionClient {

    @GET
    List<Discussion> getAllDiscussions();

    @GET
    @Path("/{id}")
    Discussion getDiscussionById(@PathParam("id") Integer id);

    @GET
    @Path("/review/{reviewId}")
    Discussion getDiscussionByReviewId(@PathParam("reviewId") Integer reviewId);

    @POST
    Discussion createDiscussion(Discussion discussion);

    @POST
    @Path("/{id}/messages")
    Message addMessage(@PathParam("id") Integer discussionId, Message message);

    @DELETE
    @Path("/{id}")
    void deleteDiscussion(@PathParam("id") Integer id);
}
