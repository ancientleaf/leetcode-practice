/**
 * https://leetcode.com/problems/maximum-subarray/
 */
public class MaxSubArray {

  public static int maxSubArrayBruteForce(int[] nums) {
    
    int max = nums[0];
 
    for(int i = 0; i< nums.length; i++) {
      int sum = 0;
      for(int j = i ; j < nums.length; j++) {
        sum += nums[j];
        if(sum > max) {
          max = sum;
        }
      }
    }

    return max;
  }

  public static int maxSubArrayDP(int[] nums) {

    int subArraymax[] = new int[nums.length];
    int max = nums[0]; 
    subArraymax[0] = nums[0];

    for(int i = 1; i < nums.length; i++) {
      int prevMax = subArraymax[i - 1];
      int currMax = Integer.max(nums[i], prevMax + nums[i]);
      max = max > currMax ? max : currMax;
      subArraymax[i] = currMax;
    }

    return max;
  }

  public static void main(String[] args) {
    int[] nums = {-2,1,-3,4,-1,2,1,-5,4};

    System.out.println(maxSubArrayBruteForce(nums));
    System.out.println(maxSubArrayDP(nums));
  }
}
