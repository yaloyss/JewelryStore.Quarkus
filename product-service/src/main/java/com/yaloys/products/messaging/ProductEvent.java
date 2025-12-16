package com.yaloys.products.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ProductEvent {

    private Integer productId;
    private String productName;
    private EventType eventType;
    private BigDecimal oldPrice;
    private BigDecimal newPrice;
    private LocalDateTime timestamp;

    public enum EventType {
        CREATED,
        UPDATED,
        PRICE_CHANGED
    }

    public ProductEvent() {
        this.timestamp = LocalDateTime.now();
    }

    public ProductEvent(Integer productId, String productName, EventType eventType) {
        this();
        this.productId = productId;
        this.productName = productName;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "ProductEvent{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", eventType=" + eventType +
                ", oldPrice=" + oldPrice +
                ", newPrice=" + newPrice +
                ", timestamp=" + timestamp +
                '}';
    }
}