package com.navi.naviStockExchange;

import com.navi.naviStockExchange.bean.Order;
import com.navi.naviStockExchange.bean.StockExchange;
import com.navi.naviStockExchange.bean.Transaction;
import com.navi.naviStockExchange.services.OrdersFileParser;

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
        List<String> result = computeTransactionResultsFromOrdersFile(file);
        result.forEach(System.out::println);
    }

    public static List<String> computeTransactionResultsFromOrdersFile(File file) {
        OrdersFileParser parser = null;
        try {
            parser = new OrdersFileParser();
            parser.parse(file);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        StockExchange stockExchange = new StockExchange();
        List<Order> orders = parser.getOrders();
        stockExchange.matchOrders(orders);
        List<Transaction> transactions = stockExchange.getExecutedTransactions();

        return transactions.stream().map(Transaction::toString).collect(Collectors.toList());
    }
}
