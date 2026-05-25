package com.yaloys.user.api;

import com.yaloys.user.clients.ProductClient;
import com.yaloys.user.models.Product;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;
import java.util.logging.Logger;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@PermitAll
public class ProductsApiResource {

    private static final Logger LOG = Logger.getLogger(ProductsApiResource.class.getName());

    @Inject
    @RestClient
    ProductClient productClient;

    @GET
    public List<Product> list() {
        return productClient.getAllProducts();
    }

    @GET
    @Path("/{id}")
    public Product get(@PathParam("id") Integer id) {
        return productClient.getProductById(id);
    }

    @POST
    public Response create(Product product) {
        try {
            Product created = productClient.createProduct(product);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (Exception e) {
            LOG.warning("Create product failed: " + e.getMessage());
            return Response.serverError().entity(MapError.of(e.getMessage())).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, Product product) {
        try {
            Product updated = productClient.updateProduct(id, product);
            return Response.ok(updated).build();
        } catch (Exception e) {
            LOG.warning("Update product failed: " + e.getMessage());
            return Response.serverError().entity(MapError.of(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Integer id) {
        try {
            productClient.deleteProduct(id);
            return Response.noContent().build();
        } catch (Exception e) {
            LOG.warning("Delete product failed: " + e.getMessage());
            return Response.serverError().entity(MapError.of(e.getMessage())).build();
        }
    }

    record MapError(String error) {
        static MapError of(String message) {
            return new MapError(message);
        }
    }
}
