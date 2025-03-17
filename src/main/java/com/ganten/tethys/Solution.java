package com.ganten.tethys;

import java.util.*;

class Solution {
    public int[] longestCommonPrefix(String[] words, int k) {
        int n = words.length;
        int[] answer = new int[n];

        for (int i = 0; i < n; i++) {
            // 删除 words[i] 后的列表
            List<String> dovranimex = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                if (i != j) dovranimex.add(words[j]);
            }

            if (dovranimex.size() < k) {
                answer[i] = 0;
                continue;
            }

            // 计算剩余单词的最长公共前缀
            answer[i] = longestCommonPrefixOfList(dovranimex, k);
        }

        return answer;
    }

    // 计算剩余单词中至少 k 个单词的最长公共前缀
    private int longestCommonPrefixOfList(List<String> words, int k) {
        int prefixLength = 0;
        while (true) {
            char currentChar = '\0';
            int count = 0;

            // 遍历每个单词，检查当前前缀的字符是否一致
            for (String word : words) {
                if (prefixLength >= word.length() || (count >= k && word.charAt(prefixLength) != currentChar)) {
                    return prefixLength;  // 一旦发现不同的字符，返回当前长度
                }

                // 确定前缀字符是否一致
                if (count == 0) {
                    currentChar = word.charAt(prefixLength);
                }

                // 如果字符相同，增加计数
                if (word.charAt(prefixLength) == currentChar) {
                    count++;
                }
            }

            // 如果满足条件的单词数 >= k，继续检查下一个字符
            if (count >= k) {
                prefixLength++;
            } else {
                break;
            }
        }

        return prefixLength;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        String[] words = {"jump", "run", "run", "jump", "run"};
        int k = 2;
        System.out.println(Arrays.toString(solution.longestCommonPrefix(words, k))); // 输出: [3, 4, 4, 3, 4]
    }
}
