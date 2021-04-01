package com.navi.naviStockExchange;

import com.navi.naviStockExchange.bean.Order;
import com.navi.naviStockExchange.bean.StockExchange;
import com.navi.naviStockExchange.bean.Transaction;
import com.navi.naviStockExchange.services.OrderFileParser;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by suyash.k
 * at 01/04/2021 15:30
 */

public class GeekTrust {
    public static void main(String[] args) {
        String filePath = args[0];
        File file = new File(filePath);
        List<String> result = run(file);
        result.forEach(System.out::println);
    }

    public static List<String> run(File file) {
        OrderFileParser parser = new OrderFileParser();
        parser.parse(file);

        StockExchange stockExchange = new StockExchange(parser.getStockDatabase());
        List<Order> orders = parser.getOrders();

        stockExchange.addAllOrders(orders);
        List<Transaction> transactions = stockExchange.getExecutedTransactions();

        return transactions.stream().map(Transaction::toString).collect(Collectors.toList());
    }
}
