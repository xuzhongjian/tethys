package com.ganten.tethys.settle;

import java.util.*;

public class OrderSettlementDFS {
    private static boolean[] used;
    private static Map<String, Double> lpBalance;
    private static List<Order> orderList;

    /**
     * 使用 DFS 回溯算法寻找最大订单结算方案
     *
     * @param orders  所有订单列表
     * @param balance 初始余额
     */
    public static List<Order> findMaxSettlement(List<Order> orders, Map<String, Double> balance) {
        used = new boolean[orders.size()];
        lpBalance = balance;
        dfs(orders, new HashMap<>(), 0);
        return orderList;
    }

    /**
     * DFS 递归函数
     *
     * @param orders     所有订单列表
     * @param userChange 当前余额
     * @param index      当前考虑的订单索引
     */
    private static void dfs(List<Order> orders, Map<String, Double> userChange, int index) {
        if (index == orders.size()) {
            return;
        }

        // 不选择当前订单
        dfs(orders, userChange, index + 1);

        if (!used[index]) {
            Order order = orders.get(index);
            String payCurrency = order.getPayCurrency();
            String gainCurrency = order.getGainCurrency();
            double payAmount = order.getPayAmount();
            double gainAmount = order.getGainAmount();


            double prevPayCurrencyAmount = userChange.getOrDefault(payCurrency, 0.0);
            double prevGainCurrencyAmount = userChange.getOrDefault(gainCurrency, 0.0);
            userChange.put(payCurrency, prevPayCurrencyAmount - payAmount);
            userChange.put(gainCurrency, prevGainCurrencyAmount + gainAmount);
            used[index] = true;

            if (canSettle(userChange)) {
                List<Order> innerOrderList = new ArrayList<>();
                for (int i = 0; i < used.length; i++) {
                    if (used[i]) {
                        innerOrderList.add(orders.get(i));
                    }
                }
                if (Objects.isNull(orderList) || innerOrderList.size() > orderList.size()) {
                    orderList = innerOrderList;
                }
            }
            dfs(orders, userChange, index + 1);

            // 退回原来的状态
            userChange.put(payCurrency, prevPayCurrencyAmount);
            userChange.put(gainCurrency, prevGainCurrencyAmount);
            used[index] = false;
        }
    }

    /**
     * 判断是否可以结算
     * 在用户需要获得的币种上，lpBalance 对应的币种应该 >= 用户需要获得的量
     *
     * @param userChange 用户当前余额
     * @return 是否可以结算
     */
    private static boolean canSettle(Map<String, Double> userChange) {
        for (Map.Entry<String, Double> entry : userChange.entrySet()) {
            String currency = entry.getKey();
            double userAmount = entry.getValue();
            if (userAmount <= 0) {
                continue;
            }
            double lpAmount = lpBalance.getOrDefault(currency, 0.0);
            if (userAmount > lpAmount) {
                return false;
            }
        }
        return true;
    }
}