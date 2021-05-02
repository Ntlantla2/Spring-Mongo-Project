package com.java.momentum.dailypoints.request;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * Request object containing the customer Id and the Product Id, the customer is represented by the customer ID and the
 * product they wish to purchase is represented by the product ID
 *
 * @Author Ntlantla Mngenela
 * @Since 2021
 */

public class PurchaseProductRequest {

    @NotBlank
    @Positive
    private Long customerId;

    @NotBlank
    @Positive
    private Long productId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
