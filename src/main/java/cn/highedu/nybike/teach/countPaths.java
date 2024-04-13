package cn.highedu.nybike.teach;

import java.util.*;

public class countPaths {
    public int countPaths(int n, int[][] roads) {
        final int mod = 1000000007;
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            graph.get(road[0]).add(new int[]{road[1], road[2]});
            graph.get(road[1]).add(new int[]{road[0], road[2]});
        }
//        new int[][]{{0, 1, 1}, {1, 2, 1}, {2, 3, 1}, {3, 4, 1}, {1, 4, 1}}
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        int[] count = new int[n];
        count[0] = 1;

        PriorityQueue<long[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));
        pq.offer(new long[]{0, 0}); // {node, distance}

        while (!pq.isEmpty()) {
            long[] curr = pq.poll();
            int node = (int) curr[0];
            long distance = curr[1];

            if (distance > dist[node]) continue;

            for (int[] neighbor : graph.get(node)) {
                int nextNode = neighbor[0];
                long nextDist = distance + neighbor[1];

                if (nextDist < dist[nextNode]) {
                    dist[nextNode] = nextDist;
                    count[nextNode] = count[node];
                    pq.offer(new long[]{nextNode, nextDist});
                } else if (nextDist == dist[nextNode]) {
                    count[nextNode] = (count[nextNode] + count[node]) % mod;
                }
            }
        }
        return count[n-1];
    }

    public static void main(String[] args) {
        countPaths cp = new countPaths();
        System.out.println(cp.countPaths(5, new int[][]{{0, 1, 1}, {1, 2, 1}, {2, 3, 1}, {3, 4, 1}, {1, 4, 1}}));
    }
}
