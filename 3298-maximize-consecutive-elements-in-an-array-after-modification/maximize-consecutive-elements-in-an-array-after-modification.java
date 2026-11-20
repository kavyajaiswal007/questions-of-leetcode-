class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        
        // Max possible value after +1 is 10^6 + 1
        int maxVal = nums[nums.length - 1] + 2;
        int[] dp = new int[maxVal];
        int maxLen = 0;
        
        for (int num : nums) {
            dp[num + 1] = dp[num] + 1;
            dp[num] = dp[num - 1] + 1;
            
            maxLen = Math.max(maxLen, Math.max(dp[num + 1], dp[num]));
        }
        
        return maxLen;
    }
}
