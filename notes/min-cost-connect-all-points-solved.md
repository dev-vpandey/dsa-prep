# Min Cost to Connect All Points — Medium
Problem Link: https://leetcode.com/problems/min-cost-to-connect-all-points/
Solved Date: 2026-06-26
Pattern Tag: graph / mst / prim's

## SRS Tracking
- Stage: 1
- Review Date: 2026-06-27
- Last Rating: —
- Review Count: 0
- Graduated: No

---

# Real World Analogy
Laying cables between cities on a grid — no cable routes are given, you measure the distance yourself. Always connect the nearest unwired city.

## Core Insight
Every point is a neighbor of every other point — there is no adjacency list. Compute Manhattan distance on the fly inside the heap loop, using the current node's index to look up its coordinates.

## Approach
Seed a min-heap with node 0 at cost 0. Poll the cheapest node — if already visited, skip. Mark visited, add cost, then loop over ALL other unvisited points, compute Manhattan distance, and push. Return total if all n points connected, else -1.

## Mental Model

╔══════════════════════════════════════════════╦══════════════════════════════════════════════════════╗
║ Decision                                     ║ Why                                                  ║
╠══════════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ No adjacency list                            ║ Graph is implicit — every pair is connected;         ║
║                                              ║ compute distance on the fly from points[]            ║
╠══════════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ curr.node is an index, not a coordinate      ║ Use points[curr.node][0] and points[curr.node][1]    ║
║                                              ║ to look up x/y — confusing curr.node with x is a    ║
║                                              ║ common bug                                           ║
╠══════════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ visited check on j, not curr.node            ║ curr.node just became visited; check the neighbor j  ║
╠══════════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ connected == n guard                         ║ Heap drains even on disconnected input; need count   ║
╚══════════════════════════════════════════════╩══════════════════════════════════════════════════════╝

## Pseudocode
```
seed heap with (node=0, cost=0)
visited = {}, total = 0, connected = 0

while heap not empty:
    poll (node, cost)
    if visited → skip
    mark visited, total += cost, connected++
    for j in 0..n-1:
        if not visited[j]:
            dist = |points[node][0] - points[j][0]| + |points[node][1] - points[j][1]|
            push (j, dist)

return connected == n ? total : -1
```

## Complexity

### Time: O(n² log n)

╔═══════════════════════╦════════════════╦══════════════════════════════════════════════════════╗
║ Component             ║ Cost           ║ Why                                                  ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════════════╣
║ Inner neighbor loop   ║ O(n) per node  ║ Loop over all n points for each of n nodes polled   ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════════════╣
║ Heap offers           ║ O(n² log n²)   ║ Up to n² entries pushed; each offer = O(log n²)     ║
║                       ║ = O(n² log n)  ║ log n² = 2 log n                                    ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════════════╣
║ Total                 ║ O(n² log n)    ║ Heap ops dominate                                    ║
╚═══════════════════════╩════════════════╩══════════════════════════════════════════════════════╝

### Space: O(n²)

╔══════════════════════╦══════════════╦══════════════════════════════════════════════════╗
║ Structure            ║ Size         ║ Why                                              ║
╠══════════════════════╬══════════════╬══════════════════════════════════════════════════╣
║ Min-heap             ║ O(n²)        ║ Each of n nodes pushes up to n neighbors         ║
╠══════════════════════╬══════════════╬══════════════════════════════════════════════════╣
║ visited set          ║ O(n)         ║ At most n nodes                                  ║
╚══════════════════════╩══════════════╩══════════════════════════════════════════════════╝

### Why Optimal
Must compare all O(n²) pairs at least once; Prim's with a key[] array (no heap) achieves O(n²) time and O(n) space for dense graphs, but heap-based is simpler and interview-safe.

╔══════════════════════════════╦══════════════╦═════════════════════════════════════════════╗
║ Algorithm                    ║ Time         ║ Note                                        ║
╠══════════════════════════════╬══════════════╬═════════════════════════════════════════════╣
║ Prim's (binary heap)         ║ O(n² log n)  ║ This solution — interview standard           ║
╠══════════════════════════════╬══════════════╬═════════════════════════════════════════════╣
║ Prim's (key[] array, dense)  ║ O(n²)        ║ Better for dense graphs; no heap overhead   ║
╠══════════════════════════════╬══════════════╬═════════════════════════════════════════════╣
║ Kruskal's (sort + UF)        ║ O(n² log n)  ║ Pre-build all edges, sort, union-find       ║
╚══════════════════════════════╩══════════════╩═════════════════════════════════════════════╝

## Watch Out For
- `curr.node` is an index — use `points[curr.node][0]` for x, `points[curr.node][1]` for y
- Visited check inside inner loop must be on `j`, not `curr.node`
- Two separate `Math.abs()` calls for Manhattan distance — one `Math.abs(dx + dy)` is wrong
- Space is O(n²), not O(n) — heap accumulates up to n² entries

## Dry Run
points = [[0,0],[2,2],[3,10],[5,2],[7,0]], n=5

```
heap=[(0,0)], visited={}, total=0, connected=0

Poll (0,0) → visited={0}, total=0, connected=1
  push (1,4),(2,13),(3,7),(4,7)

Poll (1,4) → visited={0,1}, total=4, connected=2
  push (2,9),(3,3),(4,7)

Poll (3,3) → visited={0,1,3}, total=7, connected=3
  push (2,10),(4,4)

Poll (4,4) → visited={0,1,3,4}, total=11, connected=4
  push (2,14)

Poll (3,7) → already visited, skip
Poll (4,7) → already visited, skip
Poll (4,7) → already visited, skip

Poll (2,9) → visited={0,1,2,3,4}, total=20, connected=5

connected(5) == n(5) → return 20 ✅
```

## Boilerplate Template

```java
// Prim's MST — implicit graph (compute neighbors on the fly)
int n = points.length, total = 0, connected = 0;

PriorityQueue<PointCost> minHeap = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
minHeap.offer(new PointCost(0, 0));

Set<Integer> visited = new HashSet<>();
while (!minHeap.isEmpty()) {
    var curr = minHeap.poll();
    if (visited.contains(curr.node)) continue;
    visited.add(curr.node);
    total += curr.cost;
    connected++;

    for (int j = 0; j < n; j++) {
        if (!visited.contains(j)) {
            int dist = Math.abs(points[curr.node][0] - points[j][0])
                     + Math.abs(points[curr.node][1] - points[j][1]);
            minHeap.offer(new PointCost(j, dist));
        }
    }
}
return connected == n ? total : -1;

record PointCost(int node, int cost) {}
```
