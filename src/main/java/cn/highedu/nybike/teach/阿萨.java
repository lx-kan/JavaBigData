package cn.highedu.nybike.teach;

public class 阿萨 {
    public boolean isPrime(int n) {
        if (n == 0){
            return true;
        }
        if (n < 0 || n%10 == 0){
            return false;
        }
        int len = (int) (Math.log10(n)+1);
        int ord = n, Nsum = 0;
        for (int i = 0; i < len; i++) {
           int digit = n % 10;
           Nsum = Nsum * 10 + digit;
           n /=10;
        }
        return ord == Nsum;
    }
    public int romanToInt(String s) {
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int sum = 0;
        for (int i = 0; i < symbols.length; i++) {
            while (s.startsWith(symbols[i])) {
                //"MCMXCIV"
                sum += values[i];
                s = s.substring(symbols[i].length());
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        System.out.println(new 阿萨().isPrime(1234));
        System.out.println(new 阿萨().romanToInt("MCMXCIV"));




    }
}
