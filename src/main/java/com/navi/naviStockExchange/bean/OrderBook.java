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
        List<Transaction> transactions = match(order);
        if(order.getQuantity() > 0) {
            insert(order);
        }
        return transactions;
    }

    private List<Transaction> match(Order order) {
        List<Order> candidateOrders = computeCandidateOrders(order);
        List<Transaction> transactions = new LinkedList<>();
        if(!candidateOrders.isEmpty()) {
            Iterator<Order> candidateOrderIterator = candidateOrders.iterator();
            while(order.getQuantity() > 0 && candidateOrderIterator.hasNext()) {
                Order candidateOrder = candidateOrderIterator.next();

                Transaction transaction = computeTransaction(order, candidateOrder);
                transactions.add(transaction);

                if(candidateOrder.getQuantity() == 0) {
                    remove(candidateOrder);
                }
            }
        }
        return transactions;
    }

    private Transaction computeTransaction(Order order, Order candidateOrder) {
        int quantityExecuted = Math.min(order.getQuantity(), candidateOrder.getQuantity());

        Order buyOrder, sellOrder;
        if(order.getOrderType().equals(OrderType.BUY)) {
            buyOrder = order;
            sellOrder = candidateOrder;
        } else {
            buyOrder = candidateOrder;
            sellOrder = order;
        }

        candidateOrder.setQuantity(candidateOrder.getQuantity() - quantityExecuted);
        order.setQuantity(order.getQuantity() - quantityExecuted);

        return new Transaction(buyOrder.getId(), sellOrder.getId(), sellOrder.getPrice(), quantityExecuted);
    }

    private List<Order> computeCandidateOrders(Order order) {
        TreeMap<Long, Set<Order>> ordersMap = getOppositeTypeOrderMap(order.getOrderType());
        Set<Long> candidateOrdersKeySet = computeCandidateOrdersKeySet(ordersMap, order);
        return candidateOrdersKeySet.stream().map(ordersMap::get).flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private TreeMap<Long, Set<Order>> getOppositeTypeOrderMap(OrderType orderType) {
        return getOrdersMap(OrderType.getOpposite(orderType));
    }

    private Set<Long> computeCandidateOrdersKeySet(TreeMap<Long, Set<Order>> ordersMap, Order order) {
        OrderType orderType = order.getOrderType();
        Set<Long> candidateOrdersKeySet = null;
        Long key = computeOrderKey(order);

        if(orderType.equals(OrderType.BUY)) {
            candidateOrdersKeySet = ordersMap.headMap(key, true).keySet();
        } else {
            candidateOrdersKeySet = ordersMap.tailMap(key, true).descendingKeySet();
        }
        return candidateOrdersKeySet;
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

//  this is done to ensure no problems with using double as key -> instead, assume two decimal points, multiply by 100 and save as long
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
