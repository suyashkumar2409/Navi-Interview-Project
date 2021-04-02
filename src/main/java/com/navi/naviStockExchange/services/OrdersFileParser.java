package com.navi.naviStockExchange.services;

import com.navi.naviStockExchange.bean.Factory.OrderFactory;
import com.navi.naviStockExchange.bean.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.navi.naviStockExchange.bean.Stock;
import com.navi.naviStockExchange.util.Util;

/**
 * Created by suyash.k
 * at 01/04/2021 15:14
 */

public class OrdersFileParser {
    private StockDatabase stockDatabase = new StockDatabase();
    private List<Order> orders;
    private static final int expectedColumns = 6;

    public void parse(File file) throws Exception {
        List<String> inputLines = new FileParser().parse(file);
        List<Order> list = new ArrayList<>();
        for (String inputLine : inputLines) {
            Order order = parseLine(inputLine);
            list.add(order);
        }
        orders = list;
    }

    public List<Order> getOrders() {
        return orders;
    }

    private Order parseLine(String orderInput) throws Exception {
        String[] input = Util.getTokens(orderInput, " ", expectedColumns);

        String stockName = input[2];
        stockDatabase.addStock(stockName);
        Stock stock = stockDatabase.getStock(stockName);

        return OrderFactory.createOrder(stock, input);
    }

}
