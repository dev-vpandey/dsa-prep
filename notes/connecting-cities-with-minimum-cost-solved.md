# Connecting Cities with Minimum Cost — Medium
Problem Link: https://leetcode.com/problems/connecting-cities-with-minimum-cost/
Solved Date: 2026-06-23
Pattern Tag: graph / mst / prim's

## SRS Tracking
- Stage: 2
- Review Date: 2026-06-26
- Last Rating: Strong
- Review Count: 1
- Graduated: No

---

# Real World Analogy
Laying cables between cities — connect all cities using minimum total cable. Every time you lay a cable, pick the cheapest available connection to a city not yet wired up.

## Core Insight
Always pick the cheapest edge that connects an unvisited city to the already-connected group — the greedy choice property guarantees this builds the MST.

## Approach
Build an adjacency list. Seed a min-heap with the start city at cost 0. Poll the cheapest city — if already visited, skip. Otherwise mark visited, add cost to total, and push all unvisited neighbors. Return total if all n cities connected, else -1.

## Mental Model

╔════════════════════════════════════════════╦══════════════════════════════════════════════════════╗
║ Decision                                   ║ Why                                                  ║
╠════════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ visited boolean array, not key[] array     ║ Simpler and correct; key[] alone allows re-processing║
║                                            ║ already-committed nodes if cheaper edge found later  ║
╠════════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ Start node cost = 0                        ║ It's already "in" the MST — contributes 0 to total  ║
╠════════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ Push unvisited neighbors only              ║ Avoids heap bloat; visited nodes can never be cheaper║
╠════════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ connected == n guard                       ║ Heap drains even on disconnected graph; need explicit║
║                                            ║ count to detect -1 case                             ║
╚════════════════════════════════════════════╩══════════════════════════════════════════════════════╝

## Pseudocode
```
build adjacency list from connections (undirected → add both directions)
init heap with (city=1, cost=0)
init visited = {}, total = 0, connected = 0

while heap not empty:
    poll (city, cost)
    if visited → skip
    mark visited, total += cost, connected++
    for each unvisited neighbor:
        push (neighbor, edgeCost) to heap

return connected == n ? total : -1
```

## Complexity

### Time: O(E log E)

╔═══════════════════════╦════════════════╦══════════════════════════════════════════════════════╗
║ Component             ║ Cost           ║ Why                                                  ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════════════╣
║ Build adjacency list  ║ O(E)           ║ One pass over connections array                      ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════════════╣
║ Heap offers           ║ O(E log E)     ║ Each edge pushed at most once (unvisited check);     ║
║                       ║                ║ each offer = O(log E) since heap holds ≤ E entries   ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════════════╣
║ Heap polls            ║ O(E log E)     ║ At most E polls (one per edge pushed);               ║
║                       ║                ║ each poll = O(log E)                                 ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════════════╣
║ visited.contains()    ║ O(1) avg       ║ HashSet lookup                                       ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════════════╣
║ Total                 ║ O(E log E)     ║ Heap ops dominate                                    ║
╚═══════════════════════╩════════════════╩══════════════════════════════════════════════════════╝

### Space: O(V + E)

╔══════════════════════════╦══════════════╦══════════════════════════════════════════════════╗
║ Structure                ║ Size         ║ Why                                              ║
╠══════════════════════════╬══════════════╬══════════════════════════════════════════════════╣
║ Adjacency list (graph)   ║ O(V + E)     ║ V keys in map, 2E City objects (undirected)      ║
╠══════════════════════════╬══════════════╬══════════════════════════════════════════════════╣
║ Min-heap                 ║ O(E)         ║ At most one entry per edge in worst case          ║
╠══════════════════════════╬══════════════╬══════════════════════════════════════════════════╣
║ visited set              ║ O(V)         ║ At most n cities                                 ║
╚══════════════════════════╩══════════════╩══════════════════════════════════════════════════╝

### Why Optimal
Must inspect every edge at least once (O(E) lower bound) and need an ordered structure to always pick minimum — O(E log E) is the floor with a binary heap; Fibonacci heap achieves O(E + V log V) theoretically but is never used in practice due to high constant factors.

╔══════════════════════╦══════════════════╦════════════════════════════════════════════╗
║ Algorithm            ║ Time             ║ Note                                       ║
╠══════════════════════╬══════════════════╬════════════════════════════════════════════╣
║ Prim's (binary heap) ║ O(E log E)       ║ Standard, interview-safe                   ║
╠══════════════════════╬══════════════════╬════════════════════════════════════════════╣
║ Kruskal's            ║ O(E log E)       ║ Sort edges + Union-Find; same asymptotic   ║
╠══════════════════════╬══════════════════╬════════════════════════════════════════════╣
║ Prim's (Fib heap)    ║ O(E + V log V)   ║ Better on dense graphs; impractical        ║
╚══════════════════════╩══════════════════╩════════════════════════════════════════════╝

## Watch Out For
- Cities are 1-indexed → allocate visited/arrays of size n+1
- Check visited BEFORE processing, not after adding to total
- Return -1 if connected < n — heap may drain even on disconnected graph
- Start node cost is 0 — seed heap with (city=1, cost=0), not just city
- key[] approach alone is NOT sufficient — re-processes committed nodes if cheaper edge found later

## Dry Run
n=3, connections=[[1,2,5],[1,3,6],[2,3,1]]

```
visited={}, heap=[(1,cost=0)], total=0, connected=0

Poll (1,0) → not visited ✅  visited={1}, total=0, connected=1
  push (2,5), (3,6)   heap=[(2,5),(3,6)]

Poll (2,5) → not visited ✅  visited={1,2}, total=5, connected=2
  push (3,1)  [skip 1, already visited]   heap=[(3,1),(3,6)]

Poll (3,1) → not visited ✅  visited={1,2,3}, total=6, connected=3
  [skip 1 and 2, both visited]

heap empty. connected(3) == n(3) → return 6 ✅
```

## Boilerplate Template

```java
// Prim's MST — visited array approach
Map<Integer, List<int[]>> graph = new HashMap<>();
for (int[] conn : connections) {
    graph.computeIfAbsent(conn[0], k -> new ArrayList<>()).add(new int[]{conn[1], conn[2]});
    graph.computeIfAbsent(conn[1], k -> new ArrayList<>()).add(new int[]{conn[0], conn[2]});
}

boolean[] visited = new boolean[n + 1];
PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
pq.offer(new int[]{1, 0}); // {node, cost}
int total = 0, connected = 0;

while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    int node = curr[0], cost = curr[1];
    if (visited[node]) continue;
    visited[node] = true;
    total += cost;
    connected++;
    for (int[] nbr : graph.getOrDefault(node, Collections.emptyList())) {
        if (!visited[nbr[0]]) pq.offer(nbr);
    }
}
return connected == n ? total : -1;
```
