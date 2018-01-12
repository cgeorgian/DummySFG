package com.worldpay.LLP.sym;

/**
 * Created by georgianc on 11.01.2018.
 */
public class LLPInstruction {

    private String orderId;

    private int amount;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
