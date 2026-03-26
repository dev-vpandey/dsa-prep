# Number of Connected Components in an Undirected Graph — Medium
Problem Link: [LeetCode](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/)
Solved Date: 2026-02-28
Pattern Tag: graph / dfs / connected-components
Review Date: 2026-03-21

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-06
- Last Rating: Strong
- Review Count: 3
- Graduated: No

---

# Real World Analogy
—

## Core Insight
Every time you find an unvisited node, you've found a new component — flood-fill everything reachable from it, then keep counting.

## Approach
Build an adjacency list from the edges. Loop through all nodes 0 to n-1 — whenever you hit an unvisited node, trigger a DFS to mark all reachable nodes as visited and increment your component count.

## Mental Model

```
┌─────────────────────────────────────────┬──────────────────────────────────────────────────────────────────┐
│ Decision                                │ Why                                                              │
├─────────────────────────────────────────┼──────────────────────────────────────────────────────────────────┤
│ Loop 0 to n-1 (not just edge nodes)?    │ Isolated nodes (no edges) are still valid components            │
├─────────────────────────────────────────┼──────────────────────────────────────────────────────────────────┤
│ Skip if visited.contains(i)?            │ Already counted as part of a previous component                 │
├─────────────────────────────────────────┼──────────────────────────────────────────────────────────────────┤
│ Component count in outer loop, not dfs? │ dfs marks territory — counting happens when new territory found │
├─────────────────────────────────────────┼──────────────────────────────────────────────────────────────────┤
│ Lazy-init adjList for missing nodes?    │ Nodes with no edges won't appear in edge-building loop          │
├─────────────────────────────────────────┼──────────────────────────────────────────────────────────────────┤
│ Check visited before lazy-init?         │ Skip early before doing any unnecessary work                    │
└─────────────────────────────────────────┴──────────────────────────────────────────────────────────────────┘
```

## Pseudocode
```
build adjList from edges

visited = empty set
components = 0

for each node i from 0 to n-1:
    if i already visited → skip
    if i not in adjList → add empty entry
    dfs(i)
    components++

return components

dfs(node):
    if visited → return
    mark node visited
    for each neighbor:
        dfs(neighbor)
```

## Complexity
- Time: O(V + E) — every node and edge visited exactly once
- Space: O(V + E) — adjList + visited set + recursion stack

## Watch Out For
- Nodes are 0-indexed (0 to n-1), not 1 to n
- Isolated nodes never appear in edges — handle them or adjList.get() throws NPE
- Component count lives in the OUTER loop, not inside dfs
- Check `visited` before lazy-init to avoid unnecessary work

## Dry Run
```
n=5, edges=[[0,1],[1,2],[3,4]]

adjList: {0:[1], 1:[0,2], 2:[1], 3:[4], 4:[3]}
visited={}, comp=0

i=0: not visited → dfs(0)
  visit 0→1→2, visited={0,1,2}
  comp=1

i=1: visited → skip
i=2: visited → skip

i=3: not visited → dfs(3)
  visit 3→4, visited={0,1,2,3,4}
  comp=2

i=4: visited → skip

Answer: 2 ✅
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int countComponents(int n, int[][] edges) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for(int[] edge : edges) {
            int a = edge[0], b = edge[1];
            adjList.computeIfAbsent(a, k -> new ArrayList<>()).add(b);
            adjList.computeIfAbsent(b, k -> new ArrayList<>()).add(a);
        }

        Set<Integer> visited = new HashSet<>();
        var comp = 0;
        for(var i = 0; i < n; i++) {
            if(!visited.contains(i)) {
                dfs(i, visited, adjList);
                comp++;
            }
        }
        return comp;
    }

    private void dfs(int node, Set<Integer> visited, Map<Integer, List<Integer>> adjList) {
        if(visited.contains(node)) return;
        visited.add(node);
        for(int nbr : adjList.getOrDefault(node, Collections.emptyList())) {
            dfs(nbr, visited, adjList);
        }
    }
}
```
