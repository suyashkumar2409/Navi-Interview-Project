package com.navi.naviStockExchange.bean;

import com.navi.naviStockExchange.services.StockDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by suyash.k
 * at 01/04/2021 14:55
 */

public class StockExchange {
    private final StockDatabase stockDatabase;
    private Map<Stock, OrderBook> stockOrderBook = new HashMap<>();
    private List<Transaction> transactions = new LinkedList<>();

    public StockExchange(StockDatabase stockDatabase) {
        this.stockDatabase = stockDatabase;
    }

    public void addOrder(Order order) {
        Stock stock = order.getStock();
        if(!stockOrderBook.containsKey(stock)) {
            stockOrderBook.put(stock, new OrderBook(stock));
        }

        List<Transaction> transactionsExecuted = stockOrderBook.get(stock).addOrder(order);
        transactions.addAll(transactionsExecuted);
    }

    public void addAllOrders(List<Order> orders) {
        orders.forEach(this::addOrder);
    }

    public List<Transaction> getExecutedTransactions() {
        return transactions;
    }
}
