import java.util.*;

class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        int[] totalTimes = new int[n + 1];
        Arrays.fill(totalTimes, Integer.MAX_VALUE);

        Map<Integer, List<Edge>> graph = new HashMap<>();
        for (int[] time : times) {
            int u = time[0], v = time[1], w = time[2];
            graph.computeIfAbsent(u, k1 -> new ArrayList<>()).add(new Edge(v, w));
        }

        PriorityQueue<Edge>  minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.weight, b.weight));
        minHeap.offer(new Edge(k, 0));
        totalTimes[k] = 0;

        while(!minHeap.isEmpty()) {
            var curr = minHeap.poll();
            if(curr.weight > totalTimes[curr.node]) continue;

            for(var nbr : graph.getOrDefault(curr.node, Collections.emptyList())) {
                var newTime = curr.weight + nbr.weight;
                if(newTime < totalTimes[nbr]) {
                    totalTimes[nbr] = newTime;
                    minHeap.offer(new Edge(nbr, newTime));
                }
            }
        }

        var max = 0;
        for(var i = 1; i <= n; i++) {
            if(totalTimes[i] == Integer.MAX_VALUE) return -1;
            max = Math.max(totalTimes[i], max);
        }
        return max;
    }

    record Edge(int node, int weight) {}

}