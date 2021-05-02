package com.java.momentum.dailypoints.request.response;


import javax.validation.constraints.NotBlank;

/**
 * Response object, sucessful will be true if the funds are sufficient and false otherwise with the reason as insufficient funds
 *
 * @Author Ntlantla Mngenela
 * @Since 2021
 */

public class PurchaseProductResponse {

    @NotBlank
    private boolean successful;

    private String reason;

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
