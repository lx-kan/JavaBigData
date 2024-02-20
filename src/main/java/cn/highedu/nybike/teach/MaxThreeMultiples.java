package cn.highedu.nybike.teach;

import java.util.Scanner;

public class MaxThreeMultiples {

    public static int countMultiplesOfThree(String digits) {
        int length = digits.length();
        int[] dp = new int[length + 1];

        for (int i = 1; i <= length; i++) {
            int currDigit = Character.getNumericValue(digits.charAt(i - 1));

            // 单独成为一个数字的情况
            if (currDigit % 3 == 0) {
                dp[i] = Math.max(dp[i], dp[i - 1] + 1);
            }

            // 与前面的数字组合成新数字的情况
            for (int j = i - 1; j > 0; j--) {
                int num = Integer.parseInt(digits.substring(j - 1, i));
                if (num % 3 == 0) {
                    dp[i] = Math.max(dp[i], dp[j - 1] + 1);
                }
            }
        }

        return dp[length];
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a number:");
        String number = scanner.nextLine();
        System.out.println("Maximum number of 3 multiples: " + countMultiplesOfThree(number));
        scanner.close();
    }
}



