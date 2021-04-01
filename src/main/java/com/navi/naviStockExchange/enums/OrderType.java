package com.navi.naviStockExchange.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by suyash.k
 * at 01/04/2021 17:02
 */

public enum OrderType {
    BUY("buy"),
    SELL("sell");
    private String name;

    private static final Map<String, OrderType> nameToTypeMap =
            Arrays.stream(OrderType.values()).collect(Collectors.toMap(OrderType::getName, Function.identity()));

    OrderType(String name) {
        this.name = name;
    }

    public static OrderType getOpposite(OrderType orderType) {
        if(orderType.equals(BUY))
            return SELL;
        else
            return BUY;
    }

    public String getName() {
        return name;
    }

    public static OrderType getOrderType(String orderType) {
        return nameToTypeMap.get(orderType.toLowerCase());
    }
}
