package com.navi.naviStockExchange.services;

import com.navi.naviStockExchange.bean.Order;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.navi.naviStockExchange.bean.Stock;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * Created by suyash.k
 * at 01/04/2021 15:14
 */

public class OrderFileParser {
//    private final Logger LOGGER = LogManager.getLogger(OrderFileParser.class);
    private StockDatabase stockDatabase;
    private List<Order> orders;

    public void parse(File file) {
        orders = new LinkedList<>();
        stockDatabase = new StockDatabase();
        List<String> inputLines = new FileParser().parse(file);
        for(String input : inputLines) {
            orders.add(parseLine(input));
        }
    }

    public StockDatabase getStockDatabase() {
        return stockDatabase;
    }

    public List<Order> getOrders() {
        return orders;
    }

    // TODO: 01/04/2021 error handling
    private Order parseLine(String orderInput) {
        StringTokenizer tokenizer = new StringTokenizer(orderInput, " ");
        String[] input = new String[6];
        int i=0;
        while(tokenizer.hasMoreTokens()) {
            input[i++] = tokenizer.nextToken();
        }

        String stockName = input[2];
        stockDatabase.addStock(stockName);
        Stock stock = stockDatabase.getStock(stockName);

        return OrderFactory.createOrder(input, stock);
    }
}
