# Group 4 — Graphs
# Patterns: Build Graph · DFS · BFS · Cycle Detection · Topological Sort · Bipartite · Dijkstra · Union-Find · MST
# Prerequisite: Group 2 (Queue for BFS, Stack for iterative DFS) · Group 3 (DFS recursive)
# Daily read: open this when working on any graph pattern (3 min)

---

## PREREQUISITE CHAIN
```
DFS recursive (Group 3)       Queue / BFS (Group 2)
        │                              │
        ▼                              ▼
  DFS on Graph              BFS on Graph (unweighted shortest path)
        │                              │
        ├──► Cycle detection           ├──► Multi-source BFS
        ├──► Connected components      └──► Bipartite check (2-coloring)
        └──► Topological Sort (DFS)
                                 Topological Sort (Kahn's BFS)
                                       │
                                       └──► Cycle detection in directed graph

Union-Find (standalone)
        │
        ├──► Cycle detection (undirected)
        └──► MST — Kruskal's

BFS + Heap (Group 2 Heap)
        │
        └──► Dijkstra (weighted shortest path)
                │
                └──► MST — Prim's
```

---

## ── BUILD ADJACENCY LIST ────────────────────────────────────────────────────

```java
// Undirected graph
Map<Integer, List<Integer>> graph = new HashMap<>();
for (int[] edge : edges) {
    graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);
    graph.computeIfAbsent(edge[1], k -> new ArrayList<>()).add(edge[0]);
}

// Directed graph (one direction only)
graph.computeIfAbsent(edge[0], k -> new ArrayList<>()).add(edge[1]);

// Weighted graph (Dijkstra)
Map<Integer, List<int[]>> graph = new HashMap<>();  // int[] = {neighbor, weight}
graph.computeIfAbsent(u, k -> new ArrayList<>()).add(new int[]{v, weight});
```

⚠️ Watch out: undirected graphs need BOTH directions added. Directed graphs get only one. Forgetting the reverse direction is the most common graph setup bug.

---

## ── DFS ON GRAPH ────────────────────────────────────────────────────────────
# Real world: exploring a cave system — go as deep as possible before backtracking.

```java
void dfs(int node, boolean[] visited, Map<Integer, List<Integer>> graph) {
    visited[node] = true;
    for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        if (!visited[neighbor]) {
            dfs(neighbor, visited, graph);
        }
    }
}

// Count connected components
int components = 0;
for (int i = 0; i < n; i++) {
    if (!visited[i]) { dfs(i, visited, graph); components++; }
}
```

⚠️ Watch out: mark visited BEFORE recursing — not after. Otherwise you'll revisit nodes and potentially hit infinite recursion.

---

## ── CYCLE DETECTION ─────────────────────────────────────────────────────────
# Undirected graph — node visited AND not the parent = cycle
# Directed graph — node in current DFS path (recStack) = cycle

```java
// Undirected — DFS with parent tracking
boolean hasCycle(int node, int parent, boolean[] visited, Map<Integer, List<Integer>> graph) {
    visited[node] = true;
    for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        if (!visited[neighbor]) {
            if (hasCycle(neighbor, node, visited, graph)) return true;
        } else if (neighbor != parent) return true;   // visited AND not parent = cycle
    }
    return false;
}

// Directed — DFS with recursion stack
boolean[] visited, recStack;
boolean hasCycle(int node) {
    visited[node] = true;
    recStack[node] = true;
    for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        if (!visited[neighbor] && hasCycle(neighbor)) return true;
        if (recStack[neighbor]) return true;           // back edge = cycle
    }
    recStack[node] = false;                            // remove from current path
    return false;
}
```

⚠️ Watch out: undirected uses `parent` to avoid false cycles. Directed uses `recStack` — must set it to false on backtrack.

---

## ── TOPOLOGICAL SORT ────────────────────────────────────────────────────────
# Real world: course prerequisites — you must take X before Y before Z.
# Only valid for Directed Acyclic Graphs (DAGs).

```java
// Kahn's Algorithm (BFS — preferred, detects cycle naturally)
int[] inDegree = new int[n];
for (int[] edge : edges) inDegree[edge[1]]++;          // count incoming edges

Queue<Integer> queue = new LinkedList<>();
for (int i = 0; i < n; i++) if (inDegree[i] == 0) queue.offer(i);  // start with no deps

List<Integer> order = new ArrayList<>();
while (!queue.isEmpty()) {
    int node = queue.poll();
    order.add(node);
    for (int neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        if (--inDegree[neighbor] == 0) queue.offer(neighbor);
    }
}

// Cycle check: if order.size() != n → cycle exists (some nodes never reached inDegree 0)
return order.size() == n;
```

⚠️ Watch out: if `order.size() < n` after BFS, a cycle exists — nodes in the cycle never reach inDegree 0 so they're never enqueued.

---

## ── BIPARTITE CHECK ─────────────────────────────────────────────────────────
# Real world: split people into two teams so no two teammates dislike each other.
# 2-coloring: if you can color all nodes with 2 colors (no adjacent same color) → bipartite.

```java
int[] color = new int[n];
Arrays.fill(color, -1);

boolean isBipartite(int start) {
    Queue<Integer> queue = new LinkedList<>();
    queue.offer(start);
    color[start] = 0;
    while (!queue.isEmpty()) {
        int node = queue.poll();
        for (int neighbor : graph.get(node)) {
            if (color[neighbor] == -1) {
                color[neighbor] = 1 - color[node];    // flip color
                queue.offer(neighbor);
            } else if (color[neighbor] == color[node]) return false;  // same color = not bipartite
        }
    }
    return true;
}
```

⚠️ Watch out: run bipartite check from EVERY unvisited node — graph may be disconnected.

---

## ── DIJKSTRA — SHORTEST PATH ────────────────────────────────────────────────
# Real world: Google Maps finding the fastest route — always extend the cheapest known path first.
# Prerequisite: BFS (Group 2) + Heap (Group 2) — Dijkstra IS a BFS with a priority queue.

```java
int[] dist = new int[n];
Arrays.fill(dist, Integer.MAX_VALUE);
dist[src] = 0;

PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); // {cost, node}
pq.offer(new int[]{0, src});

while (!pq.isEmpty()) {
    int[] curr = pq.poll();
    int cost = curr[0], node = curr[1];

    if (cost > dist[node]) continue;                   // stale entry — skip

    for (int[] neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        int nextNode = neighbor[0], weight = neighbor[1];
        int newCost = dist[node] + weight;
        if (newCost < dist[nextNode]) {
            dist[nextNode] = newCost;
            pq.offer(new int[]{newCost, nextNode});
        }
    }
}
```

⚠️ Watch out: `if (cost > dist[node]) continue` — the heap may have stale entries from before we found a shorter path. This single line prevents reprocessing them. Without it, Dijkstra is wrong.

---

## ── UNION-FIND ──────────────────────────────────────────────────────────────
# Real world: friend groups — find which group someone belongs to, merge groups together.
# Use when: dynamic connectivity, cycle detection, MST (Kruskal).

```java
int[] parent, rank;

void init(int n) {
    parent = new int[n];
    rank = new int[n];
    for (int i = 0; i < n; i++) parent[i] = i;
}

int find(int x) {
    if (parent[x] != x) parent[x] = find(parent[x]);  // path compression
    return parent[x];
}

boolean union(int x, int y) {
    int px = find(x), py = find(y);
    if (px == py) return false;                         // already connected = cycle
    if (rank[px] < rank[py]) { int tmp = px; px = py; py = tmp; }
    parent[py] = px;                                    // union by rank
    if (rank[px] == rank[py]) rank[px]++;
    return true;
}
```

⚠️ Watch out: `union()` returning `false` = they were already connected = adding this edge creates a cycle. This is the key signal for Redundant Connection problems.

---

## ── MST — KRUSKAL'S (Union-Find) ───────────────────────────────────────────
# Real world: connecting cities with minimum total road cost — always pick cheapest safe road.
# Prerequisite: Union-Find (above)

```java
// Sort all edges by weight, greedily pick if it doesn't form a cycle
Arrays.sort(edges, (a, b) -> a[2] - b[2]);   // edges = {u, v, weight}
init(n);
int totalCost = 0, edgesUsed = 0;

for (int[] edge : edges) {
    if (union(edge[0], edge[1])) {            // false = would form cycle, skip
        totalCost += edge[2];
        if (++edgesUsed == n - 1) break;      // MST complete (n-1 edges)
    }
}
```

---

## ── MST — PRIM'S (Min-Heap) ────────────────────────────────────────────────
# Real world: growing a tree from one city, always connecting the nearest unvisited city.
# Prerequisite: Dijkstra pattern — same heap structure.
# Best for dense graphs (many edges) like Min Cost to Connect All Points.

```java
boolean[] inMST = new boolean[n];
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]); // {cost, node}
pq.offer(new int[]{0, 0});   // start from node 0
int totalCost = 0, nodesAdded = 0;

while (!pq.isEmpty() && nodesAdded < n) {
    int[] curr = pq.poll();
    int cost = curr[0], node = curr[1];
    if (inMST[node]) continue;              // already in MST — skip
    inMST[node] = true;
    totalCost += cost;
    nodesAdded++;
    for (int[] neighbor : graph.getOrDefault(node, new ArrayList<>())) {
        if (!inMST[neighbor[0]]) pq.offer(new int[]{neighbor[1], neighbor[0]});
    }
}
```

⚠️ Watch out: `if (inMST[node]) continue` — same stale entry guard as Dijkstra. Without it you'll add nodes to MST multiple times.

---

## Your Recurring Mistakes — Group 4
- **Graph setup**: forgetting both directions for undirected graphs
- **DFS**: marking visited AFTER recursing instead of before
- **Cycle directed**: not resetting `recStack[node] = false` on backtrack
- **Topological sort**: not checking `order.size() == n` for cycle detection
- **Dijkstra**: missing `if (cost > dist[node]) continue` stale entry guard
- **Union-Find**: not using path compression — find becomes O(n) instead of O(α(n))
- **Prim's / Dijkstra**: forgetting `if (inMST[node]) continue` / stale check

---

## Solved Card Examples
- DFS / BFS: `number-of-islands-solved.md` · `clone-graph-solved.md`
- Cycle + Components: `graph-valid-tree-solved.md` · `number-of-connected-components-solved.md`
- Topological Sort: `course-schedule-solved.md`
- Bipartite: `is-graph-bipartite-solved.md` · `possible-bipartition-solved.md`
- Dijkstra: `dijkstras-shortest-path-solved.md`
- Union-Find: `redundant-connection-solved.md`
- BFS shortest path: `word-ladder-solved.md`
- Multi-source: `pacific-atlantic-water-flow-solved.md` · `surrounded-regions-solved.md`
