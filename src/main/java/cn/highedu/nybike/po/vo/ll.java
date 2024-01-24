package cn.highedu.nybike.po.vo;

public class ll {
        public static long minimumRemoval(int[] beans) {
            int[] cnt = new int[100001];
            long sum = 0, n = beans.length, ans = Long.MIN_VALUE, idx = 0;
            for (int b : beans) {
                cnt[b]++;
                sum += b;
            }
            for (int i = 0; i < 100001; ++i) {
                ans = Math.max(ans, (n - idx) * i);
                idx += cnt[i];
            }
            return sum - ans;
        }
    public static void main(String[] args) {
        int[] beans = {4, 1, 6, 5};
        System.out.println("Minimum beans to be removed: " + minimumRemoval(beans));
    }

}
