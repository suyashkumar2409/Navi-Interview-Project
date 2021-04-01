package com.navi.naviStockExchange.bean;

import com.navi.naviStockExchange.enums.OrderType;
import com.navi.naviStockExchange.util.Constants;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by suyash.k
 * at 01/04/2021 14:56
 */

public class OrderBook {
    private Stock stock;
    private TreeMap<Long, Set<Order>> buyOrders = new TreeMap<>();
    private TreeMap<Long, Set<Order>> sellOrders = new TreeMap<>();

    public OrderBook(Stock stock) {
        this.stock = stock;
    }

    public TreeMap<Long, Set<Order>> getOrdersMap(OrderType orderType) {
        if(orderType.equals(OrderType.BUY)) {
            return buyOrders;
        } else {
            return sellOrders;
        }
    }

    public List<Transaction> addOrder(Order order) {
        List<Transaction> transactions = execute(order);
        if(order.getQuantity() > 0) {
            insert(order);
        }
        return transactions;
    }

    private List<Transaction> execute(Order order) {
        List<Order> candidateOrders = computeCandidateOrders(order);
        List<Transaction> transactions = new LinkedList<>();
        if(!candidateOrders.isEmpty()) {
            Iterator<Order> candidateOrderIterator = candidateOrders.iterator();
            while(order.getQuantity() > 0 && candidateOrderIterator.hasNext()) {
                Order candidateOrder = candidateOrderIterator.next();
                int quantityExecuted = Math.min(order.getQuantity(), candidateOrder.getQuantity());

                Order buyOrder, sellOrder;
                if(order.getOrderType().equals(OrderType.BUY)) {
                    buyOrder = order;
                    sellOrder = candidateOrder;
                } else {
                    buyOrder = candidateOrder;
                    sellOrder = order;
                }

                Transaction transaction = new Transaction(buyOrder.getId(), sellOrder.getId(), sellOrder.getPrice(), quantityExecuted);
                transactions.add(transaction);

                if(candidateOrder.getQuantity() == quantityExecuted) {
                    remove(candidateOrder);
                }

                order.setQuantity(order.getQuantity() - quantityExecuted);
            }
        }
        return transactions;
    }

    private List<Order> computeCandidateOrders(Order order) {
        TreeMap<Long, Set<Order>> ordersMap = getOrdersMap(OrderType.getOpposite(order.getOrderType()));
        Long key = computeOrderKey(order);

        Set<Long> candidateOrdersKeySet;
        if(order.getOrderType().equals(OrderType.BUY)) {
            candidateOrdersKeySet = ordersMap.headMap(key, true).keySet();
        } else {
            candidateOrdersKeySet = ordersMap.tailMap(key, true).keySet();
        }

        List<Order> candidateOrders = candidateOrdersKeySet.stream().map(ordersMap::get).flatMap(Collection::stream)
                .sorted(Comparator.comparing(Order::getId)).collect(Collectors.toList());
        return candidateOrders;
    }

    private void remove(Order order) {
        Long key = computeOrderKey(order);
        TreeMap<Long, Set<Order>> ordersMap = getOrdersMap(order.getOrderType());

        if(ordersMap.containsKey(key)) {
            ordersMap.get(key).remove(order);
            if(ordersMap.get(key).size() == 0) {
                ordersMap.remove(key);
            }
        }
    }

    private void insert(Order order) {
        TreeMap<Long, Set<Order>> ordersMap = getOrdersMap(order.getOrderType());
        insert(ordersMap, order);
    }

    private Long computeOrderKey(Order order) {
        return (long)(order.getPrice()* Constants.priceKeyMultiplier);
    }

    private void insert(TreeMap<Long, Set<Order>> ordersMap, Order order) {
        Long key = computeOrderKey(order);
        if(!ordersMap.containsKey(key)) {
            ordersMap.put(key, new HashSet<>());
        }
        ordersMap.get(key).add(order);
    }
}
