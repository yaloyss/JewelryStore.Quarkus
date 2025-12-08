package com.yaloys.products.controllers;


import com.yaloys.products.models.Metal;
import com.yaloys.products.repositories.MetalRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/metals")
//@Produces(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class MetalResource {

    @Inject
    MetalRepository metalRepository;

    @GET
    public List<Metal> getAllMetals() {
        return metalRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getMetalById(@PathParam("id") Integer id) {
        return metalRepository.findById(id)
                .map(metal -> Response.ok(metal).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response createMetal(Metal metal) {
        Metal saved = metalRepository.save(metal);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateMetal(@PathParam("id") Integer id, Metal metal) {
        if (!metalRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        metal.setMetalId(id);
        Metal updated = metalRepository.save(metal);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteMetal(@PathParam("id") Integer id) {
        if (!metalRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        metalRepository.deleteById(id);
        return Response.noContent().build();
    }
}