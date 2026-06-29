# Optimize Water Distribution in a Village — Hard
Problem Link: https://leetcode.com/problems/optimize-water-distribution-in-a-village/
Solved Date: 2026-06-28
Pattern Tag: graph / mst / virtual-node / prim's

## SRS Tracking
- Stage: 1
- Review Date: 2026-06-29
- Last Rating: —
- Review Count: 0
- Graduated: No

---

# Real World Analogy
Setting up water supply for a village — each house can drill its own well (fixed cost) or receive water via a pipe from a neighbor. You want all houses supplied at minimum total cost. "Drilling a well" = connecting to a virtual water tower node.

## Core Insight
Add a virtual node 0 (water source) with edges to every house i at cost wells[i-1], then find MST of this augmented graph — Prim's naturally picks wells vs pipes optimally.

## Approach
Build adjacency list with virtual node 0 connected to every house via well cost. Add all pipe edges bidirectionally. Run Prim's from node 0. Because node 0 connects to every house, the graph is always fully connected — no disconnection check needed.

## Mental Model

╔══════════════════════════════════════════╦══════════════════════════════════════════════════════╗
║ Decision                                 ║ Why                                                  ║
╠══════════════════════════════════════════╬══════════════════════════════════════════════════════╣
║ Virtual node 0                           ║ Converts "build well" into an edge — MST handles it  ║
║ Start Prim's from node 0                 ║ Node 0 is the universal connector                    ║
║ No connected check                       ║ Node 0 guarantees all houses always reachable        ║
║ Mark visited only on poll                ║ Lazy deletion — allows cheaper path to win           ║
╚══════════════════════════════════════════╩══════════════════════════════════════════════════════╝

## Pseudocode
```
for i = 1..n: add edges 0↔i with cost wells[i-1]
for each pipe [a,b,c]: add edges a↔b with cost c
minHeap.offer(node=0, cost=0)
while heap not empty:
  poll (node, cost)
  if visited: skip
  mark visited, total += cost
  push unvisited neighbors
return total
```

## Complexity

### Time: O((V + E) log E)

╔═══════════════════════╦════════════════╦══════════════════════════════════════════════╗
║ Component             ║ Cost           ║ Why                                          ║
╠═══════════════════════╬════════════════╬══════════════════════════════════════════════╣
║ Build graph           ║ O(V + E)       ║ n well edges + |pipes| pipe edges            ║
║ Prim's heap ops       ║ O(E log E)     ║ each edge pushed/popped once                 ║
║ Total                 ║ O((V+E) log E) ║ heap dominates                               ║
╚═══════════════════════╩════════════════╩══════════════════════════════════════════════╝

### Space: O(V + E)

╔══════════════════╦══════════╦══════════════════════════════════════════════╗
║ Structure        ║ Size     ║ Why                                          ║
╠══════════════════╬══════════╬══════════════════════════════════════════════╣
║ Graph            ║ O(V+E)   ║ n+1 nodes, n well edges + |pipes|           ║
║ Heap             ║ O(E)     ║ at most all edges queued                     ║
║ Visited set      ║ O(V)     ║ n+1 nodes                                   ║
╚══════════════════╩══════════╩══════════════════════════════════════════════╝

### Why Optimal
Must touch every edge at least once to find MST — O(E log E) is the theoretical floor for comparison-based MST.

╔══════════════════════╦══════════════╦════════════════════════════════════════════╗
║ Algorithm / Approach ║ Time         ║ Note                                       ║
╠══════════════════════╬══════════════╬════════════════════════════════════════════╣
║ Prim's + virtual 0   ║ O(E log E)   ║ This solution                              ║
╠══════════════════════╬══════════════╬════════════════════════════════════════════╣
║ Kruskal's + DSU      ║ O(E log E)   ║ Same complexity, sort all edges then union ║
╚══════════════════════╩══════════════╩══════════════════════════════════════════════╝

## Watch Out For
- Do NOT mark visited when pushing to heap — only on poll (lazy deletion)
- wells[] is 0-indexed but houses are 1-indexed — use `wells[i-1]`
- Virtual node is node 0, so total nodes = n+1 in your mental model
- No connected check needed — virtual node 0 guarantees every house is reachable

## Dry Run
`n=2, wells=[5,1], pipes=[[1,2,10]]`
```
Graph: 0→[(1,5),(2,1)], 1→[(0,5),(2,10)], 2→[(0,1),(1,10)]
Heap: [(0,0)]
Poll (0,0): visited={0}, total=0. Push (1,5),(2,1)
Poll (2,1): visited={0,2}, total=1. Push (1,10), skip 0
Poll (1,5): visited={0,1,2}, total=6. Done.
Return 6 ✅  (well house2=1 + well house1=5, pipe too expensive at 10)
```

## Boiler Plate Template

```java
class Solution {
    public int minCostToSupplyWater(int n, int[] wells, int[][] pipes) {
        int total = 0;
        Map<Integer, List<HouseCost>> graph = new HashMap<>();

        for (var i = 1; i <= n; i++) {
            graph.computeIfAbsent(0, k -> new ArrayList<>()).add(new HouseCost(i, wells[i - 1]));
            graph.computeIfAbsent(i, k -> new ArrayList<>()).add(new HouseCost(0, wells[i - 1]));
        }
        for (var pipe : pipes) {
            int a = pipe[0], b = pipe[1], c = pipe[2];
            graph.computeIfAbsent(a, k -> new ArrayList<>()).add(new HouseCost(b, c));
            graph.computeIfAbsent(b, k -> new ArrayList<>()).add(new HouseCost(a, c));
        }

        PriorityQueue<HouseCost> minHeap = new PriorityQueue<>((a, b) -> a.cost - b.cost);
        minHeap.offer(new HouseCost(0, 0));
        Set<Integer> visited = new HashSet<>();

        while (!minHeap.isEmpty()) {
            var curr = minHeap.poll();
            int currNode = curr.edge, currCost = curr.cost;
            if (visited.contains(currNode)) continue;
            visited.add(currNode);
            total += currCost;
            for (var nbr : graph.getOrDefault(currNode, Collections.emptyList()))
                if (!visited.contains(nbr.edge)) minHeap.offer(new HouseCost(nbr.edge, nbr.cost));
        }

        return total;
    }

    record HouseCost(int edge, int cost) {}
}
```
