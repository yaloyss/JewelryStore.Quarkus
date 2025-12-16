package com.yaloys.products.messaging;

import io.smallrye.reactive.messaging.annotations.Broadcast;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.jboss.logging.Logger;
import jakarta.inject.Inject;

@ApplicationScoped
public class ProductEventPublisher {

    private static final Logger LOG = Logger.getLogger(ProductEventPublisher.class);

    @Inject
    @Channel("product-events")
    @Broadcast
    Emitter<ProductEvent> productEventEmitter;

    public void publishProductCreated(Integer productId, String productName) {
        ProductEvent event = new ProductEvent(productId, productName, ProductEvent.EventType.CREATED);
        LOG.info("Publishing CREATED event for product: " + productId);
        productEventEmitter.send(event);
    }

    public void publishProductUpdated(Integer productId, String productName) {
        ProductEvent event = new ProductEvent(productId, productName, ProductEvent.EventType.UPDATED);
        LOG.info("Publishing UPDATED event for product: " + productId);
        productEventEmitter.send(event);
    }

    public void publishPriceChanged(Integer productId, String productName, java.math.BigDecimal oldPrice, java.math.BigDecimal newPrice) {
        ProductEvent event = new ProductEvent(productId, productName, ProductEvent.EventType.PRICE_CHANGED);
        event.setOldPrice(oldPrice);
        event.setNewPrice(newPrice);
        LOG.info("Publishing PRICE_CHANGED event for product: " + productId + " (Old: " + oldPrice + ", New: " + newPrice + ")");
        productEventEmitter.send(event);
    }

}