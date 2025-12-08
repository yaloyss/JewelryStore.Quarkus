package com.yaloys.products.controllers;

import com.yaloys.products.models.Category;
import com.yaloys.products.repositories.CategoryRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/api/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {

    @Inject
    CategoryRepository categoryRepository;

    @GET
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @GET
    @Path("/{id}")
    public Response getCategoryById(@PathParam("id") Integer id) {
        return categoryRepository.findById(id)
                .map(category -> Response.ok(category).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @POST
    public Response createCategory(Category category) {
        Category saved = categoryRepository.save(category);
        return Response.status(Response.Status.CREATED).entity(saved).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCategory(@PathParam("id") Integer id, Category category) {
        if (!categoryRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        category.setCategoryId(id);
        Category updated = categoryRepository.save(category);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCategory(@PathParam("id") Integer id) {
        if (!categoryRepository.existsById(id)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        categoryRepository.deleteById(id);
        return Response.noContent().build();
    }
}