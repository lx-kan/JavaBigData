package cn.highedu.nybike.teach;

import java.util.HashSet;
import java.util.Set;

public class 无重复字符最长字符串 {
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>();
        int maxLength = 0;
        int i = 0, j = 0;
        while (j < s.length()) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                maxLength = Math.max(maxLength, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }
        return maxLength;
    }
    public static void main(String[] args) {
        无重复字符最长字符串 solution = new 无重复字符最长字符串();
        String s = "abcbcbc";
        System.out.println(solution.lengthOfLongestSubstring(s));
    }
}
