package com.yaloys.user;

import com.yaloys.user.clients.OrderClient;
import com.yaloys.user.clients.ProductClient;
import com.yaloys.user.clients.ReviewClient;
import com.yaloys.user.models.Order;
import com.yaloys.user.models.Product;
import com.yaloys.user.models.Review;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
//
//@Path("/")
//public class UserResource {
//
//    @Inject
//    Template index;
//
//    @Inject
//    Template home;
//
//    @Inject
//    @RestClient
//    OrderClient orderClient;
//
//    @Inject
//    @RestClient
//    ProductClient productClient;
//
//    @Inject
//    @RestClient
//    ReviewClient reviewClient;
//
//    @Inject
//    SecurityIdentity securityIdentity;
//
//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public TemplateInstance index() {
//        if (securityIdentity.isAnonymous()) {
//            return index.instance();
//        }
//        return home.data("user", getCurrentUser());
//    }
//
//    @GET
//    @Path("/home")
//    @Authenticated
//    @Produces(MediaType.TEXT_HTML)
//    public TemplateInstance homePage() {
//        return home.data("user", getCurrentUser());
//    }
//
//    @GET
//    @Path("/products")
//    @Authenticated
//    @Produces(MediaType.TEXT_HTML)
//    public TemplateInstance products() {
//        List<Product> products = productClient.getAllProducts();
//        System.out.println(products);
//        return home.data("user", getCurrentUser())
//                .data("products", products);
//    }
//
//    @GET
//    @Path("/orders")
//    @Authenticated
//    @Produces(MediaType.TEXT_HTML)
//    public TemplateInstance orders() {
//        List<Order> orders = orderClient.getAllOrders();
//        return home.data("user", getCurrentUser())
//                .data("orders", orders);
//    }
//
//    @GET
//    @Path("/reviews")
//    @Authenticated
//    @Produces(MediaType.TEXT_HTML)
//    public TemplateInstance reviews() {
//        List<Review> reviews = reviewClient.getAllReviews();
//        return home.data("user", getCurrentUser())
//                .data("reviews", reviews);
//    }
//
//    private Map<String, String> getCurrentUser() {
//        Map<String, String> user = new HashMap<>();
//        user.put("name", securityIdentity.getPrincipal().getName());
//        user.put("email", securityIdentity.getPrincipal().getName());
//        user.put("role", securityIdentity.getRoles().toString());
//        return user;
//    }
//}

@Path("/")
public class UserResource {

    private static final Logger LOG = Logger.getLogger(String.valueOf(UserResource.class));

    @Inject
    Template index;

    @Inject
    Template home;

    @Inject
    Template products;

    @Inject
    Template orders;

    @Inject
    Template reviews;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    @RestClient
    ProductClient productClient;

    @Inject
    @RestClient
    OrderClient orderClient;

    @Inject
    @RestClient
    ReviewClient reviewClient;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public Response index() {
        return Response.seeOther(URI.create("/index.html")).build();
    }

    @GET
    @Path("/home")
    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    public Response homePage() {
        return Response.seeOther(URI.create("/index.html")).build();
    }

    @GET
    @Path("/products")
    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    public Response productsPage() {
        return Response.seeOther(URI.create("/index.html")).build();
    }

    @POST
    @Path("/products/create")
//    @Authenticated
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createProduct(
            @FormParam("name") String name,
            @FormParam("price") BigDecimal price,
            @FormParam("weight") BigDecimal weight,
            @FormParam("size") BigDecimal size,
            @FormParam("manufacturer") String manufacturer,
            @FormParam("metalId") Integer metalId,
            @FormParam("categoryId") Integer categoryId) {

        LOG.info("Creating new product: " + name);

        try {
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setWeight(weight);
            product.setSize(size);
            product.setManufacturer(manufacturer);
            product.setMetalId(metalId);
            product.setCategoryId(categoryId);

            Product created = productClient.createProduct(product);
            LOG.info("Product created successfully: " + created.getProductId());

            return Response.seeOther(URI.create("/index.html")).build();

        } catch (Exception e) {
            LOG.info("Error creating product: " + e.getMessage());
            return Response.seeOther(URI.create("/index.html")).build();
        }
    }

    @POST
    @Path("/products/update")
//    @Authenticated
    @PermitAll
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response updateProduct(
            @FormParam("productId") Integer productId,
            @FormParam("name") String name,
            @FormParam("price") BigDecimal price,
            @FormParam("weight") BigDecimal weight,
            @FormParam("size") BigDecimal size,
            @FormParam("manufacturer") String manufacturer,
            @FormParam("metalId") Integer metalId,
            @FormParam("categoryId") Integer categoryId) {

        LOG.info("Updating product: " + productId);

        try {
            Product product = new Product();
            product.setProductId(productId);
            product.setName(name);
            product.setPrice(price);
            product.setWeight(weight);
            product.setSize(size);
            product.setManufacturer(manufacturer);
            product.setMetalId(metalId);
            product.setCategoryId(categoryId);

            productClient.updateProduct(productId, product);
            LOG.info("Product updated successfully: " + productId);

            return Response.seeOther(URI.create("/index.html")).build();
        } catch (Exception e) {
            LOG.info("Error updating product: " + e.getMessage());
            return Response.seeOther(URI.create("/index.html")).build();
        }
    }

    @POST
    @Path("/products/delete/{id}")
//    @Authenticated
    @PermitAll
    public Response deleteProduct(@PathParam("id") Integer id) {
        LOG.info("Deleting product: " + id);

        try {
            productClient.deleteProduct(id);
            LOG.info("Product deleted successfully: " + id);

            return Response.seeOther(URI.create("/index.html")).build();
        } catch (Exception e) {
            LOG.info("Error deleting product: " + e.getMessage());
            return Response.seeOther(URI.create("/index.html")).build();
        }
    }
    @GET
    @Path("/orders")
    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    public Response ordersPage() {
        return Response.seeOther(URI.create("/index.html")).build();
    }

    @GET
    @Path("/reviews")
    @PermitAll
    @Produces(MediaType.TEXT_HTML)
    public Response reviewsPage() {
        return Response.seeOther(URI.create("/index.html")).build();
    }

    private Map<String, String> getCurrentUser() {
        Map<String, String> user = new HashMap<>();
        user.put("name", securityIdentity.getPrincipal().getName());
        user.put("email", securityIdentity.getPrincipal().getName());
        user.put("role", securityIdentity.getRoles().toString());
        return user;
    }
}