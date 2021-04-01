package com.navi.naviStockExchange.bean;

/**
 * Created by suyash.k
 * at 01/04/2021 15:12
 */

public class Transaction {
    private Long buyOrderId;
    private Long sellOrderId;
    private double price;
    private int quantity;

    public Transaction(Long buyOrderId, Long sellOrderId, double price, int quantity) {
        this.buyOrderId = buyOrderId;
        this.sellOrderId = sellOrderId;
        this.price = price;
        this.quantity = quantity;
    }

    public String toString() {
        return String.format("#%d %.2f %d #%d", buyOrderId, price, quantity, sellOrderId);
    }
}
