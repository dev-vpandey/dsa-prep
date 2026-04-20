# Redundant Connection — Medium
Problem Link: https://leetcode.com/problems/redundant-connection/
Solved Date: 2026-03-28
Pattern Tag: union-find

## SRS Tracking
- Stage: 1
- Review Date: 2026-03-30
- Last Rating: Okay
- Review Count: 1
- Graduated: No

---

# Real World Analogy
Friend groups at a party — each person starts as their own group. As introductions happen, groups merge. The moment you try to introduce two people who are already in the same group, that introduction is redundant.

## Core Insight
Process edges one by one — the first edge that connects two nodes already in the same group is the cycle-forming redundant edge.

## Approach
Initialize every node as its own group. For each edge, check if both endpoints share the same root (representative). If yes, this edge creates a cycle — return it. If no, merge the two groups and continue.

## Mental Model
╔══════════════════════════════════════╦══════════════════════════════════════════════════════════════════╗
║ Decision                             ║ Why                                                              ║
╠══════════════════════════════════════╬══════════════════════════════════════════════════════════════════╣
║ Use roots, not nodes, in union       ║ Root = group ID. Two nodes in same group share same root.        ║
║ Union by rank                        ║ Attach shorter tree under taller — keeps tree flat, find fast    ║
║ Path compression in find             ║ Flatten tree on every find so future finds are O(1)              ║
║ Check find(u)==find(v) before union  ║ Lets us capture and return the redundant edge                    ║
║ Array size n+1                       ║ Nodes are 1-indexed                                              ║
║ rank[rootX]++ only on equal rank     ║ Only time tree height actually increases                         ║
╚══════════════════════════════════════╩══════════════════════════════════════════════════════════════════╝

## Pseudocode
```
init parent[i] = i for all nodes
init rank[i] = 0

for each edge [u, v]:
    if find(u) == find(v):
        return edge
    union(u, v)

find(x):
    if parent[x] != x:
        parent[x] = find(parent[x])
    return parent[x]

union(x, y):
    rootX = find(x), rootY = find(y)
    if rootX == rootY: return
    if rank[rootX] < rank[rootY]: parent[rootX] = rootY
    if rank[rootX] > rank[rootY]: parent[rootY] = rootX
    else: parent[rootY] = rootX, rank[rootX]++
```

## Complexity
- Time: O(n · α(n)) ≈ O(n) — path compression + rank make each find/union near O(1)
- Space: O(n) — parent and rank arrays of size n+1

## Watch Out For
- `rank[rootX]++` not `rank[x]++` — increment the root's rank, not the original node
- Compare `rank[rootX] < rank[rootY]`, not node values `rootX < rootY`
- Array size must be `n+1` since nodes are 1-indexed

## Dry Run
Input: `[[1,2],[1,3],[2,3]]`

╔════════╦══════════╦══════════╦═══════╦══════════════╗
║ Edge   ║ find(u)  ║ find(v)  ║ Same? ║ Action       ║
╠════════╬══════════╬══════════╬═══════╬══════════════╣
║ [1,2]  ║ 1        ║ 2        ║ NO    ║ parent[2]=1  ║
║ [1,3]  ║ 1        ║ 3        ║ NO    ║ parent[3]=1  ║
║ [2,3]  ║ 1        ║ 1        ║ YES   ║ return [2,3] ║
╚════════╩══════════╩══════════╩═══════╩══════════════╝

## Boiler Plate Template
```java
class Solution {
    int[] parent;
    int[] rank;

    public int[] findRedundantConnection(int[][] edges) {
        var n = edges.length;
        parent = new int[n + 1];
        rank = new int[n + 1];

        for (var i = 1; i <= n; i++) parent[i] = i;

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            if (find(u) == find(v)) return edge;
            union(u, v);
        }
        return new int[] {};
    }

    private int find(int x) {
        if (parent[x] != x)
            parent[x] = find(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        int rootX = find(x), rootY = find(y);
        if (rootX == rootY) return;
        if (rank[rootX] < rank[rootY]) parent[rootX] = rootY;
        else if (rank[rootX] > rank[rootY]) parent[rootY] = rootX;
        else { parent[rootY] = rootX; rank[rootX]++; }
    }
}
```
