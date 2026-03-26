# Graph Valid Tree — Medium
Problem Link: https://leetcode.com/problems/graph-valid-tree/
Solved Date: 2026-03-22
Pattern Tag: graph / dfs / connected-components / tree-validation
Review Date: 2026-03-25

## SRS Tracking
- Stage: 2
- Review Date: 2026-03-25
- Last Rating: —
- Review Count: 0
- Graduated: No

---

# Real World Analogy
—

## Core Insight
A valid tree has exactly two properties: connected (1 component) and acyclic.
For n nodes, acyclic + connected ≡ exactly n-1 edges + 1 component — no explicit cycle detection needed.

## Approach
Check `edges.length == n-1` upfront (rules out cycles cheaply). Then DFS to count
connected components — if count == 1, the graph is fully connected. Both conditions
together guarantee a valid tree.

## Mental Model
```
┌─────────────────────────┬────────────────────────────────────────────────────┐
│ Decision                │ Why                                                │
├─────────────────────────┼────────────────────────────────────────────────────┤
│ Check n-1 edges first   │ Cheapest guard — rules out cycles and              │
│                         │ disconnected graphs with extra edges immediately   │
├─────────────────────────┼────────────────────────────────────────────────────┤
│ Count components via DFS│ If count == 1, all nodes reachable from any root   │
├─────────────────────────┼────────────────────────────────────────────────────┤
│ No explicit cycle check │ n-1 edges + connected ≡ acyclic by definition      │
├─────────────────────────┼────────────────────────────────────────────────────┤
│ getOrDefault for nodes  │ Isolated nodes won't appear in adjList             │
│ with no edges           │ — avoids NPE                                       │
└─────────────────────────┴────────────────────────────────────────────────────┘
```

## Pseudocode
```
if edges.length != n - 1 → return false

build undirected adjList
count = 0, visited = {}

for i in 0..n-1:
    if not visited:
        dfs(i)
        count++

return count == 1

dfs(node):
    if visited → return
    mark visited
    for each neighbor: dfs(neighbor)
```

## Complexity
- Time: O(V + E) — every node and edge visited once
- Space: O(V + E) — adjList + visited + recursion stack

## Why this is optimal
Can't do better — must read all edges at least once to verify connectivity.

## Watch Out For
- Must check `edges.length == n-1` BEFORE DFS — a connected cyclic graph passes the component check alone
- Undirected graph → add edge both ways in adjList
- Use `getOrDefault` for isolated nodes to avoid NPE

## Dry Run
```
n=5, edges=[[0,1],[0,2],[0,3],[1,4]]
edges.length=4 == n-1=4 ✓

DFS from 0 → visits 0,1,2,3,4 → count=1
return true ✓

n=5, edges=[[0,1],[1,2],[2,3],[1,3],[1,4]]
edges.length=5 != n-1=4 → return false ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public boolean validTree(int n, int[][] edges) {
        if (edges.length != n - 1) return false;

        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            int a = edge[0], b = edge[1];
            graph.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
            graph.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
        }

        Set<Integer> visited = new HashSet<>();
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (!visited.contains(i)) {
                dfs(graph, visited, i);
                count++;
            }
        }
        return count == 1;
    }

    private void dfs(Map<Integer, List<Integer>> graph, Set<Integer> visited, int node) {
        if (visited.contains(node)) return;
        visited.add(node);
        for (int nbr : graph.getOrDefault(node, Collections.emptyList()))
            dfs(graph, visited, nbr);
    }
}
```

