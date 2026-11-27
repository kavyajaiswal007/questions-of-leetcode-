class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int[] firstCopyValue = new int[n];

        // 1. Calculate the total items gained from buying the 1st copy of each item
        for (int i = 0; i < n; i++) {
            int count = 1; // 1 for the purchased item itself
            for (int j = 0; j < n; j++) {
                if (i != j && items[j][0] % items[i][0] == 0) {
                    count++;
                }
            }
            firstCopyValue[i] = count;
        }

        // 2. DP array to store max items for a given cost
        int[] dp = new int[budget + 1];

        for (int i = 0; i < n; i++) {
            int price = items[i][1];
            int val = firstCopyValue[i];

            if (price > budget) continue;

            // Step A: 0/1 Knapsack for the 1st copy (gives 1 + free items)
            for (int w = budget; w >= price; w--) {
                dp[w] = Math.max(dp[w], dp[w - price] + val);
            }

            // Step B: Unbounded Knapsack for 2nd+ copies (gives only 1 item each)
            for (int w = price * 2; w <= budget; w++) {
                dp[w] = Math.max(dp[w], dp[w - price] + 1);
            }
        }

        return dp[budget];
    }
}
