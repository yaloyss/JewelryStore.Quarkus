package com.yaloys.user.clients;

import com.yaloys.user.models.Product;
import io.quarkus.oidc.token.propagation.common.AccessToken;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@Path("/api/products")
//@AccessToken
@RegisterRestClient(configKey = "product-service")
public interface ProductClient {

    @GET
    List<Product> getAllProducts();

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Product getProductById(@PathParam("id") Integer id);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Product createProduct(Product product);

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Product updateProduct(@PathParam("id") Integer id, Product product);

    @DELETE
    @Path("/{id}")
    void deleteProduct(@PathParam("id") Integer id);
}
