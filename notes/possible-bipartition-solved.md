# Possible Bipartition — Medium
Link: [LeetCode](https://leetcode.com/problems/possible-bipartition/)
Date: 2026-02-26

## Core Insight
If you model dislikes as edges in a graph, the problem reduces to checking whether the graph is 2-colorable (bipartite).

## Approach
Build an undirected adjacency list from the dislikes pairs. BFS/DFS from every unvisited node, assigning alternating colors (group A / group B). If you ever try to assign a node the same color as its neighbor, a valid split is impossible.

## Pseudocode
```
build adjacency list from dislikes
color[1..n] = 0 (uncolored)

for each uncolored node:
    BFS from that node, color it 1
    for each neighbor:
        if uncolored → assign opposite color, enqueue
        if same color as current → return false

return true
```

## Complexity
- Time: O(N + E)
- Space: O(N + E)

## Watch Out For
- Graph may be disconnected — loop over all nodes, not just from node 1
- People are 1-indexed — allocate arrays of size n+1
- Must make the dislike graph undirected (add edge both ways)

## Why No Level-Order Traversal in Bipartite BFS

In typical BFS (shortest path, tree levels) you care about **distance** — you process level by level using `size = queue.size()` to separate them.

In bipartite BFS, you don't care about levels at all. You only care about one thing:
> When I visit a node, give it the **opposite color** of whoever enqueued it.

The `color[]` array **replaces** level tracking entirely — even depth = group 1, odd depth = group 2. If two adjacent nodes share the same color, you have a contradiction. No level info needed.

```
┌──────────────────────┬──────────────────────────────┬──────────────────────────────┐
│                      │ Level-order BFS              │ Bipartite BFS                │
├──────────────────────┼──────────────────────────────┼──────────────────────────────┤
│ Goal                 │ Track distance/depth         │ Track group assignment       │
│ Cares about levels?  │ Yes                          │ No                           │
│ Extra bookkeeping    │ size = queue.size() loop     │ Just the color[] array       │
│ When to act          │ After processing full level  │ Immediately when dequeuing   │
└──────────────────────┴──────────────────────────────┴──────────────────────────────┘
```

## Pattern Tag
bipartite-check / graph-coloring / bfs
