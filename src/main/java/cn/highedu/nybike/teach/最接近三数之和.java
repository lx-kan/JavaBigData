package cn.highedu.nybike.teach;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 最接近三数之和 {
    public int threeSumClosest(int[] nums, int target) {
        // 对数组进行排序
        Arrays.sort(nums);
        int minDiff = Integer.MAX_VALUE;
        int closestSum = 0;
        // 遍历所有可能的组合
        for (int i = 0; i < nums.length; i++) {
            // 使用双指针查找两个元素
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                int diff = Math.abs(sum - target);

                // 更新最小差值和最接近目标值的和
                if (diff < minDiff) {
                    minDiff = diff;
                    closestSum = sum;
                }
                // 移动指针
                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    // 找到精确解，直接返回
                    return target;
                }
            }
        }
        return closestSum;
    }
}
