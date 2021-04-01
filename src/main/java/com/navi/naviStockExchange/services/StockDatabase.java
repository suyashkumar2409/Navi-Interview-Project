package com.navi.naviStockExchange.services;

import com.navi.naviStockExchange.bean.Stock;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by suyash.k
 * at 01/04/2021 15:32
 */

public class StockDatabase {
    private Map<String, Stock> stockMap = new HashMap<>();

    public void addStock(String stockName) {
        if(!stockMap.containsKey(stockName)) {
            Stock stock = new Stock(stockName);
            stockMap.put(stockName, stock);
        }
    }

    public Stock getStock(String stockName) {
        return stockMap.get(stockName);
    }
}
