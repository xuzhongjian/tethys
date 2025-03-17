package com.ganten.tethys.settle;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        // 创建初始货币余额
        Map<String, Double> lpAmount = new HashMap<>();
        lpAmount.put("USD", 1000.0);
        lpAmount.put("EUR", 20.0);
        lpAmount.put("GBP", 900.0);

        // 创建订单
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(120.0, "EUR", 100.0, "USD"));
        orders.add(new Order(90.0, "GBP", 100.0, "EUR"));
        orders.add(new Order(110.0, "USD", 100.0, "GBP"));
        orders.add(new Order(105.0, "USD", 100.0, "EUR"));
        orders.add(new Order(95.0, "EUR", 100.0, "GBP"));
//        orders.add(new Order(85.0, "GBP", 100.0, "USD"));
//        orders.add(new Order(80.0, "USD", 100.0, "EUR"));
//        orders.add(new Order(125.0, "EUR", 100.0, "GBP"));
//        orders.add(new Order(135.0, "GBP", 100.0, "USD"));
//        orders.add(new Order(78.0, "USD", 100.0, "EUR"));
//        orders.add(new Order(100.0, "EUR", 100.0, "GBP"));
//        orders.add(new Order(140.0, "GBP", 100.0, "USD"));
//        orders.add(new Order(92.0, "USD", 100.0, "EUR"));
//        orders.add(new Order(130.0, "EUR", 100.0, "GBP"));
//        orders.add(new Order(120.0, "GBP", 100.0, "USD"));
//        orders.add(new Order(88.0, "USD", 100.0, "EUR"));
//        orders.add(new Order(110.0, "EUR", 100.0, "GBP"));
//        orders.add(new Order(145.0, "GBP", 100.0, "USD"));
//        orders.add(new Order(77.0, "USD", 100.0, "EUR"));
//        orders.add(new Order(115.0, "EUR", 100.0, "GBP"));
//        orders.add(new Order(138.0, "GBP", 100.0, "USD"));
//        orders.add(new Order(85.0, "USD", 100.0, "EUR"));
//        orders.add(new Order(100.0, "EUR", 100.0, "GBP"));
//        orders.add(new Order(150.0, "GBP", 100.0, "USD"));
//        orders.add(new Order(72.0, "USD", 100.0, "EUR"));
//        orders.add(new Order(98.0, "EUR", 100.0, "GBP"));
//        orders.add(new Order(132.0, "GBP", 100.0, "USD"));
//        orders.add(new Order(79.0, "USD", 100.0, "EUR"));
//        orders.add(new Order(105.0, "EUR", 100.0, "GBP"));
//        orders.add(new Order(125.0, "GBP", 100.0, "USD"));

        long startTime = System.currentTimeMillis();
        List<Order> resultOrders = OrderSettlementDFS.findMaxSettlement(orders, lpAmount);
        long endTime = System.currentTimeMillis();

        System.out.println("总订单数量: " + orders.size());
        System.out.println("可结算订单: " + resultOrders.size());
        System.out.println("执行时间: " + (endTime - startTime) + " ms");
        printResults(resultOrders, lpAmount);
    }


    public static void printResults(List<Order> orders, Map<String, Double> lpAmount) {
        System.out.println("\n=== 订单结算结果 ===");
        if (orders.isEmpty()) {
            System.out.println("没有可结算的订单。");
            return;
        }

        // 打印订单列表
        System.out.println("完成的订单数: " + orders.size());
        System.out.println("----------------------------------------");
        System.out.printf("| %-3s | %-10s | %-6s | %-10s | %-6s |\n", "#", "支付金额", "支付币种", "获得金额", "获得币种");
        System.out.println("----------------------------------------");

        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            System.out.printf("| %-3d | %-10.2f | %-6s | %-10.2f | %-6s |\n", (i + 1), order.getPayAmount(), order.getPayCurrency(), order.getGainAmount(), order.getGainCurrency());
        }
        System.out.println("----------------------------------------");

        // 计算用户净变化
        Map<String, Double> netChange = new HashMap<>();
        for (Order order : orders) {
            netChange.put(order.getPayCurrency(), netChange.getOrDefault(order.getPayCurrency(), 0.0) - order.getPayAmount());
            netChange.put(order.getGainCurrency(), netChange.getOrDefault(order.getGainCurrency(), 0.0) + order.getGainAmount());
        }

        // 记录 LP 余额（结算前）
        Map<String, Double> lpBalanceBefore = new HashMap<>(lpAmount);

        // 计算 LP 结算后余额
        for (Map.Entry<String, Double> entry : netChange.entrySet()) {
            String currency = entry.getKey();
            double change = entry.getValue();
            lpAmount.put(currency, lpAmount.getOrDefault(currency, 0.0) - change);
        }

        // 获取所有涉及的货币
        Set<String> allCurrencies = new TreeSet<>();
        allCurrencies.addAll(netChange.keySet());
        allCurrencies.addAll(lpBalanceBefore.keySet());
        allCurrencies.addAll(lpAmount.keySet());

        // 打印对比表
        System.out.println("\n=== 资金变动对比表 ===");
        System.out.println("---------------------------------------------------");
        System.out.printf("| %-6s | %-10s | %-10s | %-10s |\n", "币种", "用户净变化", "LP 余额", "LP 结算后余额");
        System.out.println("---------------------------------------------------");

        for (String currency : allCurrencies) {
            double userChange = netChange.getOrDefault(currency, 0.0);
            double lpBefore = lpBalanceBefore.getOrDefault(currency, 0.0);
            double lpAfter = lpAmount.getOrDefault(currency, 0.0);

            System.out.printf("| %-6s | %+-10.2f | %-10.2f | %-10.2f |\n", currency, userChange, lpBefore, lpAfter);
        }

        System.out.println("---------------------------------------------------");
        System.out.println("\n=== 执行完成 ===");
    }
}
