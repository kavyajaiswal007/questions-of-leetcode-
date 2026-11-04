class Solution {
    private long[] bitCnt, bitSum;
    private int m;

    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        int[] vals = nums.clone();
        Arrays.sort(vals);
        m = 0;
        for (int i = 0; i < n; i++)
            if (i == 0 || vals[i] != vals[i - 1]) vals[m++] = vals[i];

        int[] rank = new int[n];
        for (int i = 0; i < n; i++)
            rank[i] = Arrays.binarySearch(vals, 0, m, nums[i]) + 1;

        bitCnt = new long[m + 1];
        bitSum = new long[m + 1];

        // cost[s] = cost to make window [s, s+x) equal
        long[] cost = new long[n - x + 1];
        int half = (x + 1) / 2;
        long total = 0;
        for (int i = 0; i < n; i++) {
            update(rank[i], 1, nums[i]);
            total += nums[i];
            if (i >= x) {
                update(rank[i - x], -1, -nums[i - x]);
                total -= nums[i - x];
            }
            if (i >= x - 1) {
                int med = kth(half);
                long v = vals[med - 1];
                long cL = prefixCnt(med), sL = prefixSum(med);
                long cR = x - cL, sR = total - sL;
                cost[i - x + 1] = v * cL - sL + sR - v * cR;
            }
        }

        // dp over number of windows chosen, rolling array
        final long INF = Long.MAX_VALUE / 4;
        long[] prev = new long[n + 1];   // 0 windows: cost 0
        for (int j = 1; j <= k; j++) {
            long[] cur = new long[n + 1];
            cur[0] = INF;
            for (int i = 1; i <= n; i++) {
                long best = cur[i - 1];
                if (i >= x && prev[i - x] < INF)
                    best = Math.min(best, prev[i - x] + cost[i - x]);
                cur[i] = best;
            }
            prev = cur;
        }
        return prev[n];
    }

    private void update(int pos, long c, long s) {
        for (int p = pos; p <= m; p += p & -p) {
            bitCnt[p] += c;
            bitSum[p] += s;
        }
    }

    private long prefixCnt(int p) {
        long r = 0;
        for (; p > 0; p -= p & -p) r += bitCnt[p];
        return r;
    }

    private long prefixSum(int p) {
        long r = 0;
        for (; p > 0; p -= p & -p) r += bitSum[p];
        return r;
    }

    // smallest rank whose prefix count >= kk
    private int kth(int kk) {
        int pos = 0;
        for (int step = Integer.highestOneBit(m); step > 0; step >>= 1) {
            if (pos + step <= m && bitCnt[pos + step] < kk) {
                pos += step;
                kk -= bitCnt[pos];
            }
        }
        return pos + 1;
    }
}