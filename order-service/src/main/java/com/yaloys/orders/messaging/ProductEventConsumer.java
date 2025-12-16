package com.yaloys.orders.messaging;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;
import io.vertx.core.json.JsonObject;

@ApplicationScoped
public class ProductEventConsumer {

    private static final Logger LOG = Logger.getLogger(ProductEventConsumer.class);

    @Incoming("product-events")
    public void consumeProductEvent(JsonObject json) {
        ProductEvent event = json.mapTo(ProductEvent.class);

        LOG.info("=== Received Product Event ===");
        LOG.info("Event Type: " + event.getEventType());
        LOG.info("Product ID: " + event.getProductId());
        LOG.info("Product Name: " + event.getProductName());

        switch (event.getEventType()) {
            case CREATED:
                LOG.info("Processing CREATED event for product: " + event.getProductName());
                break;
            case UPDATED:
                LOG.info("Processing UPDATED event for product: " + event.getProductName());
                break;
            case PRICE_CHANGED:
                LOG.info("Processing PRICE_CHANGED event");
                LOG.info("Product: " + event.getProductName());
                LOG.info("Old Price: " + event.getOldPrice());
                LOG.info("New Price: " + event.getNewPrice());
                break;
        }
        LOG.info("=== Product Event Processed ===");
    }
}