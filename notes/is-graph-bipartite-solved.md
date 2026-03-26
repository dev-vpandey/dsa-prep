# Is Graph Bipartite? — Medium
Problem Link: https://leetcode.com/problems/is-graph-bipartite/
Solved Date: 2026-03-25
Pattern Tag: graph / bfs / 2-coloring / bipartite-check

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like seating guests at a wedding — can you split them into two tables such that everyone's enemies sit at the opposite table? If any enemy-pair ends up at the same table, it's not bipartite.

## Core Insight
A graph is bipartite iff it can be 2-colored with no two adjacent nodes sharing a color. BFS/DFS: assign color 0 to start, color 1 to neighbors. If a neighbor already has the same color as current, return false. Handle disconnected components.

## Approach
Use a `color[]` array initialized to -1. For each unvisited node, start BFS. Assign color 0, enqueue. For each neighbor: if uncolored → assign opposite color, enqueue. If colored same as current → not bipartite.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ color[] with -1 as unvisited         │ Tracks both visited state and color   │
│                                      │ assignment in one array               │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Opposite color = 1 - color[cur]      │ Toggles between 0 and 1 cleanly       │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Outer loop over all nodes            │ Graph may be disconnected — must      │
│                                      │ check every component                 │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
color = int[-1, -1, ..., -1]  // size n

for each node i:
    if color[i] != -1: continue
    color[i] = 0
    queue = [i]
    while queue not empty:
        cur = queue.poll()
        for each neighbor of cur:
            if color[neighbor] == -1:
                color[neighbor] = 1 - color[cur]
                queue.add(neighbor)
            elif color[neighbor] == color[cur]:
                return false

return true
```

## Complexity
- Time: O(V + E) — visit every node and edge once
- Space: O(V) — color array + BFS queue

## Watch Out For
- Handle disconnected graph — outer loop over all nodes
- `1 - color[cur]` is the cleanest way to flip between 0 and 1
- Check conflict when neighbor is ALREADY colored (same color = fail)

## Dry Run
```
graph = [[1,3],[0,2],[1,3],[0,2]]

Start at 0: color[0]=0, queue=[0]
  Neighbors 1,3: color[1]=1, color[3]=1
Start at 1: color[1]=1, queue=[1]
  Neighbor 0: color[0]=0 ≠ 1 ✓
  Neighbor 2: color[2]=0
Start at 2: color[2]=0
  Neighbor 1: color[1]=1 ≠ 0 ✓
  Neighbor 3: color[3]=1 ≠ 0 ✓
All good → return true ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;
        int[] color = new int[n];
        Arrays.fill(color, -1);

        for (int i = 0; i < n; i++) {
            if (color[i] != -1) continue;  // already colored
            color[i] = 0;
            Queue<Integer> queue = new ArrayDeque<>();
            queue.offer(i);
            while (!queue.isEmpty()) {
                int cur = queue.poll();
                for (int neighbor : graph[cur]) {
                    if (color[neighbor] == -1) {
                        color[neighbor] = 1 - color[cur];
                        queue.offer(neighbor);
                    } else if (color[neighbor] == color[cur]) {
                        return false;  // same color = conflict
                    }
                }
            }
        }
        return true;
    }
}
```
