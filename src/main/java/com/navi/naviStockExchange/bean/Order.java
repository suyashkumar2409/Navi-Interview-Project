package com.navi.naviStockExchange.bean;

/**
 * Created by suyash.k
 * at 01/04/2021 15:01
 */

import com.navi.naviStockExchange.enums.OrderType;

public class Order {
    private Long id;
    private String timeStamp;
    private Stock stock;
    private OrderType orderType;
    private double price;
    private int quantity;

    public Order(Long id, String timeStamp, Stock stock, OrderType orderType, double price, int quantity) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.stock = stock;
        this.orderType = orderType;
        this.price = price;
        this.quantity = quantity;
    }

    Stock getStock() {
        return stock;
    }

    OrderType getOrderType() {
        return orderType;
    }

    double getPrice() {
        return price;
    }

    Long getId() {
        return id;
    }

    int getQuantity() {
        return quantity;
    }

    void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean equals(Object a) {
        if(!(a instanceof Order)) {
            return false;
        }
        return ((Order) a).id.equals(this.id);
    }
}
