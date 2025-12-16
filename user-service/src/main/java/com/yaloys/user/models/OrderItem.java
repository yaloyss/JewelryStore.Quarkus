package com.yaloys.user.models;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class OrderItem {
    private Integer productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
