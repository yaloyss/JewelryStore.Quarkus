package com.yaloys.products.controllers;


import com.yaloys.products.models.Stone;
import com.yaloys.products.repositories.StoneRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/api/stones")
//@Produces(MediaType.APPLICATION_JSON)
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public class StoneResource {

    @Inject
    StoneRepository stoneRepository;

    @GET
    public List<Stone> getAllStones() {
        return com.yaloys.products.repositories.StoneRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getStoneById(@PathParam("id") Integer id) {
        return com.yaloys.products.repositories.StoneRepository.findById(id)
                .map(stone -> Response.ok(stone).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response createStone(Stone stone) {
        Stone saved = com.yaloys.products.repositories.StoneRepository.save(stone);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateStone(@PathParam("id") Integer id, Stone stone) {
        if (!com.yaloys.products.repositories.StoneRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        stone.setStoneId(id);
        Stone updated = com.yaloys.products.repositories.StoneRepository.save(stone);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStone(@PathParam("id") Integer id) {
        if (!com.yaloys.products.repositories.StoneRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        com.yaloys.products.repositories.StoneRepository.deleteById(id);
        return Response.noContent().build();
    }
}