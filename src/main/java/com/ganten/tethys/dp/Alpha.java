package com.ganten.tethys.dp;

public class Alpha {
    /**
     * dp 矩阵的第 i，j 号元素：对于第 i 个物品，如果背包容量为 j 时，能够取得的最大价值。
     * 行号 i 表示第 i 个物品，从前面 wights、values 列表可知，第 0 个物品的重量和价值均为 0 。
     * 列号 j 表示当背包的容量为 j 时，对应的最大价值是多少。
     */
    private int backpack(int[] wights, int[] values, int capacity) {
        int size = wights.length;
        int[][] dp = new int[size + 1][capacity + 1];
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= capacity; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j >= wights[i]) {
                    // 当背包容量大于等于第i个物品的重量时，可能有两种情况
                    // 1. 取上一个物品的价值（不拿此物品）
                    // 2. 扣除此物品容量后，上一个物品的价值（拿此物品），取其中较大的一个
                    int prevValues = dp[i - 1][j - wights[i]];
                    dp[i][j] = Math.max(dp[i][j], prevValues + values[i]);
                }
            }
        }
        return dp[size][capacity];
    }
}
