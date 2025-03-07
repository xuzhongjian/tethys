package com.ganten.tethys.daily.year2025.month3;

import java.util.ArrayList;
import java.util.List;

public class D7 {
    public int[] closestPrimes(int left, int right) {
        List<Integer> list = new ArrayList<>();
        for (int i = left; i <= right; i++) {
            if (this.isPrime(i)) {
                list.add(i);
            }
        }
        if (list.size() <= 1) {
            return new int[]{-1, -1};
        }
        int m = list.get(0);
        int n = list.get(1);
        if (list.size() == 2) {
            return new int[]{m, n};
        }
        int min = n - m;
        for (int i = 2; i < list.size(); i++) {
            int minInner = list.get(i) - list.get(i - 1);
            if (minInner < min) {
                min = minInner;
                m = list.get(i - 1);
                n = list.get(i);
            }
        }
        return new int[]{m, n};
    }

    public boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        D7 d7 = new D7();
        d7.closestPrimes(19, 31);
    }
}
