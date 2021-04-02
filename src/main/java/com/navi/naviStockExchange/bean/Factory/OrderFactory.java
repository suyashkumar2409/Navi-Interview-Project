package com.navi.naviStockExchange.bean.Factory;

import com.navi.naviStockExchange.bean.Order;
import com.navi.naviStockExchange.bean.Stock;
import com.navi.naviStockExchange.enums.OrderType;

/**
 * Created by suyash.k
 * at 01/04/2021 15:50
 */

public class OrderFactory {
    public static Order createOrder(Stock stock, String[] input) throws Exception {
        Long orderId = Long.parseLong(input[0].split("#")[1]);
        OrderType orderType = OrderType.getOrderType(input[3]);
        if(orderType == null) {
            throw new Exception(String.format("Invalid order type::%s", input[3]));
        }
        return new Order(orderId, input[1], stock, orderType, Double.parseDouble(input[4]), Integer.parseInt(input[5]));
    }
}
