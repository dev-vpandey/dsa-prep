# Dijkstra's Shortest Path — Medium
Problem Link: https://bytebytego.com/exercises/coding-patterns/graphs/shortest-path
Solved Date: 2026-04-04
Pattern Tag: graph / dijkstra / min-heap

## SRS Tracking
- Stage: 1
- Review Date: 2026-04-05
- Last Rating: —
- Review Count: 0
- Graduated: No

---

# Real World Analogy
GPS navigation — you're finding the fastest route from your home to every other location in the city. At each step you always explore the nearest unvisited destination first, updating travel times as you discover faster routes.

## Core Insight
Always process the globally cheapest unvisited node next — a min-heap gives you this in O(log V), and once a node is settled its distance is final (no negative weights can improve it later).

## Approach
Build an adjacency list from the edge list. Use a min-heap seeded with the start node at cost 0. Each time you poll the cheapest entry, skip it if already visited, otherwise settle it and offer all its neighbors only if the new path is strictly shorter than what's known.

## Mental Model

╔══════════════════════════════════════════╦══════════════════════════════════════════════════════╗
║ Decision                                 ║ Why                                                  ║
╠══════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ Min-heap ordered by distance             ║ Guarantees we always settle the cheapest node next   ║
║ Check visited on POLL, not on offer      ║ Stale entries can exist in heap — skip them at poll  ║
║ Distance check before offering           ║ Prevents heap bloat — only add strictly better paths ║
║ Update distances[nbr] before offering    ║ Heap entry must match the recorded best distance     ║
║ Fill distances with MAX_VALUE            ║ Represents unreachable — return -1 for these         ║
╚══════════════════════════════════════════╩══════════════════════════════════════════════════════╝

## Pseudocode
```
build adjList from edges (both directions for undirected)
fill distances[n] with MAX_VALUE
distances[start] = 0
minHeap.offer(start, 0)

while heap not empty:
    poll (currNode, currDist)
    if visited → skip
    mark visited

    for each neighbor (nbrNode, weight):
        if not visited:
            newDist = currDist + weight
            if newDist < distances[nbrNode]:
                distances[nbrNode] = newDist
                heap.offer(nbrNode, newDist)

return distances (MAX_VALUE → -1 for unreachable)
```

## Complexity

### Time: O((V + E) log V)

╔═══════════════════════╦════════════════╦══════════════════════════════════════════════╗
║ Component             ║ Cost           ║ Why                                          ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════╣
║ Build adjacency list  ║ O(E)           ║ One pass through all edges                   ║
║ Heap poll (per node)  ║ O(V log V)     ║ Each node settled once × log V per poll      ║
║ Neighbor processing   ║ O(E log V)     ║ Each edge visited once × log V per offer     ║
║ Total                 ║ O((V+E) log V) ║ Dominated by edge processing                 ║
╚═══════════════════════╩════════════════╩══════════════════════════════════════════════╝

### Space: O(V + E)

╔══════════════════╦══════════╦══════════════════════════════════════════════╗
║ Structure        ║ Size     ║ Why                                          ║
╠══════════════════╬══════════╬══════════════════════════════════════════════╣
║ Adjacency list   ║ O(V + E) ║ V lists + E total edge entries               ║
║ distances array  ║ O(V)     ║ One entry per node                           ║
║ visited set      ║ O(V)     ║ At most V nodes settled                      ║
║ min-heap         ║ O(V)     ║ Distance check keeps heap bounded at O(V)    ║
╚══════════════════╩══════════╩══════════════════════════════════════════════╝

## Why this is optimal
You must examine every edge at least once and each heap operation costs O(log V) — O((V+E) log V) with a binary heap is the practical optimum; Fibonacci heap gives O(V log V + E) but is never used in interviews.

## Watch Out For
- `distances[start] = 0` not `distances[0]` — easy to hardcode wrong
- Visited check fires on **poll**, not on offer — stale entries are discarded lazily
- Distance check before offering — without it heap grows to O(E) and you get O(E log E)
- Unreachable nodes stay at `MAX_VALUE` — return `-1` for these in the result
- Comparator `(a, b) -> a.dist - b.dist` can overflow for large weights — use `Integer.compare(a.dist, b.dist)` to be safe

## Dry Run
Graph: n=6, edges=[[0,1,5],[0,2,3],[1,2,1],[1,3,4],[2,3,4],[2,4,5]], start=0

```
Poll (0,0)  → offer (1,5) (2,3)          distances=[0,5,3,∞,∞,∞]
Poll (2,3)  → offer (1,4✅) (3,7) (4,8)  distances=[0,4,3,7,8,∞]  ← beats old (1,5)
Poll (1,4)  → 3: 4+4=8 > 7 ❌ skip       distances unchanged
Poll (1,5)  → already visited, skip (stale)
Poll (3,7)  → all neighbors visited
Poll (4,8)  → all neighbors visited
Result: [0, 4, 3, 7, 8, -1]
```

## Boiler Plate Template

```java
import java.util.*;

public class Main {
    public static ArrayList<Integer> shortest_path(int n, ArrayList<ArrayList<Integer>> edges, int start) {
        Map<Integer, List<Edge>> adjList = new HashMap<>();
        for (ArrayList<Integer> edge : edges) {
            int a = edge.get(0), b = edge.get(1), c = edge.get(2);
            adjList.computeIfAbsent(a, k -> new ArrayList<>()).add(new Edge(b, c));
            adjList.computeIfAbsent(b, k -> new ArrayList<>()).add(new Edge(a, c)); // remove for directed
        }

        int[] distances = new int[n];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>((a, b) -> Integer.compare(a.dist, b.dist));
        minHeap.offer(new Edge(start, 0));

        while (!minHeap.isEmpty()) {
            var curr = minHeap.poll();
            int currNode = curr.node, currDist = curr.dist;
            if (visited.contains(currNode)) continue;
            visited.add(currNode);

            for (var nbr : adjList.getOrDefault(currNode, Collections.emptyList())) {
                if (!visited.contains(nbr.node)) {
                    int newDist = currDist + nbr.dist;
                    if (newDist < distances[nbr.node]) {
                        distances[nbr.node] = newDist;
                        minHeap.offer(new Edge(nbr.node, newDist));
                    }
                }
            }
        }

        ArrayList<Integer> res = new ArrayList<>();
        for (int d : distances) res.add(d == Integer.MAX_VALUE ? -1 : d);
        return res;
    }
}

class Edge {
    int node, dist;
    Edge(int node, int dist) { this.node = node; this.dist = dist; }
}
```
