# Group 2 — Linear Structures
# Patterns: Stack · Monotonic Stack · Queue · BFS · Multi-source BFS · Heap
# Prerequisite: Group 1 (HashMap for BFS visited tracking)
# Daily read: open this when working on any Group 2 pattern (3 min)

---

## PREREQUISITE CHAIN
```
Array / HashMap (Group 1)
    │
    ├──► Stack → Monotonic Stack (next greater, largest rectangle)
    │
    └──► Queue → BFS → Multi-source BFS → 0-1 BFS
                  │
                  └──► Heap (priority-ordered BFS = Dijkstra → see Group 4)
```

---

## ── STACK ───────────────────────────────────────────────────────────────────
# Real world: a stack of plates — you always add/remove from the top only.

```java
Deque<Integer> stack = new ArrayDeque<>();
stack.push(x);       // add to top
stack.pop();         // remove from top (throws if empty)
stack.peek();        // look at top, don't remove (throws if empty)
stack.isEmpty();
stack.size();

// Safe peek/pop
if (!stack.isEmpty()) stack.peek();
```

⚠️ Watch out: use `Deque<Integer>` NOT `Stack<Integer>`. `Stack` is legacy and slow. `ArrayDeque` is the correct choice.

---

## ── MONOTONIC STACK ─────────────────────────────────────────────────────────
# Real world: a bouncer line — shorter people get pushed out when a taller person arrives.
# Use when: next greater/smaller element, largest rectangle, trapping rain water.

```java
// Next Greater Element (monotonic decreasing stack — stores indices)
int[] result = new int[n];
Arrays.fill(result, -1);
Deque<Integer> stack = new ArrayDeque<>();  // stores INDICES

for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && arr[stack.peek()] < arr[i]) {
        result[stack.pop()] = arr[i];       // i is the next greater for popped index
    }
    stack.push(i);
}

// Next Smaller Element (monotonic increasing stack)
for (int i = 0; i < n; i++) {
    while (!stack.isEmpty() && arr[stack.peek()] > arr[i]) {
        result[stack.pop()] = arr[i];
    }
    stack.push(i);
}

// Circular array — iterate twice
for (int i = 0; i < 2 * n; i++) {
    int idx = i % n;
    while (!stack.isEmpty() && arr[stack.peek()] < arr[idx]) {
        result[stack.pop()] = arr[idx];
    }
    if (i < n) stack.push(i);              // only push in first pass
}
```

⚠️ Watch out: always store INDICES in the stack, not values — you need the index to fill the result array. Values can be retrieved as `arr[stack.peek()]`.

---

## ── QUEUE / BFS ─────────────────────────────────────────────────────────────
# Real world: a checkout line — first person in is the first person served.
# BFS explores nodes level by level — guarantees shortest path in unweighted graphs.

```java
// Standard BFS on graph
Queue<Integer> queue = new LinkedList<>();
boolean[] visited = new boolean[n];
queue.offer(start);
visited[start] = true;

while (!queue.isEmpty()) {
    int node = queue.poll();
    for (int neighbor : graph.get(node)) {
        if (!visited[neighbor]) {
            visited[neighbor] = true;
            queue.offer(neighbor);
        }
    }
}

// Level-order BFS — capture one full level at a time
while (!queue.isEmpty()) {
    int size = queue.size();                // ← snapshot BEFORE inner loop
    for (int i = 0; i < size; i++) {
        int node = queue.poll();
        // process node
        for (int neighbor : graph.get(node)) {
            if (!visited[neighbor]) {
                visited[neighbor] = true;
                queue.offer(neighbor);
            }
        }
    }
    level++;                                // one full level done
}

// BFS on grid (4-directional)
int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
Queue<int[]> queue = new LinkedList<>();
queue.offer(new int[]{startR, startC});
boolean[][] visited = new boolean[m][n];
visited[startR][startC] = true;

while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    for (int[] d : dirs) {
        int nr = cell[0] + d[0], nc = cell[1] + d[1];
        if (nr < 0 || nr >= m || nc < 0 || nc >= n) continue;
        if (visited[nr][nc]) continue;
        visited[nr][nc] = true;
        queue.offer(new int[]{nr, nc});
    }
}
```

⚠️ Watch out: `int size = queue.size()` MUST be captured before the inner `for` loop — queue grows as you enqueue children, which corrupts the level boundary.

---

## ── MULTI-SOURCE BFS ────────────────────────────────────────────────────────
# Real world: multiple fires spreading simultaneously — all start at the same "distance 0".
# Use when: distance from multiple sources (Walls & Gates, Rotting Oranges).

```java
Queue<int[]> queue = new LinkedList<>();

// Seed ALL sources first before starting BFS
for (int r = 0; r < m; r++) {
    for (int c = 0; c < n; c++) {
        if (grid[r][c] == SOURCE) {
            queue.offer(new int[]{r, c});
            visited[r][c] = true;
        }
    }
}

// Then standard BFS from here — all sources expand simultaneously
while (!queue.isEmpty()) {
    int[] cell = queue.poll();
    for (int[] d : dirs) {
        int nr = cell[0] + d[0], nc = cell[1] + d[1];
        if (/* out of bounds or invalid */) continue;
        // process and enqueue
    }
}
```

⚠️ Watch out: seed ALL sources BEFORE starting the BFS loop. If you BFS from one source, then add the next, distances will be wrong.

---

## ── HEAP / PRIORITY QUEUE ───────────────────────────────────────────────────
# Real world: a hospital emergency room — most critical patient always seen next.

```java
// Min-heap (default)
PriorityQueue<Integer> minHeap = new PriorityQueue<>();

// Max-heap
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

// Custom comparator — int[] sorted by index 1 ascending
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);

// Custom — sort by frequency asc, then lex desc (K most frequent strings)
PriorityQueue<String> pq = new PriorityQueue<>(
    (a, b) -> freq.get(a).equals(freq.get(b)) ? b.compareTo(a) : freq.get(a) - freq.get(b)
);

pq.offer(x);     // add
pq.poll();       // remove and return min/max
pq.peek();       // view min/max without removing
pq.size();
pq.isEmpty();

// K largest elements — min-heap of size K (evict smallest)
PriorityQueue<Integer> heap = new PriorityQueue<>();
for (int num : nums) {
    heap.offer(num);
    if (heap.size() > k) heap.poll();    // remove smallest → keeps K largest
}
// heap.peek() = Kth largest

// K smallest elements — max-heap of size K (evict largest)
PriorityQueue<Integer> heap = new PriorityQueue<>(Collections.reverseOrder());
for (int num : nums) {
    heap.offer(num);
    if (heap.size() > k) heap.poll();    // remove largest → keeps K smallest
}

// Merge K sorted lists / arrays — heap of (value, list index, element index)
PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[0] - b[0]);
// push first element of each list, then poll + push next from same list

// Two-heap pattern (find median)
PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder()); // lower half
PriorityQueue<Integer> minHeap = new PriorityQueue<>();                           // upper half
// invariant: maxHeap.size() == minHeap.size() OR maxHeap.size() == minHeap.size() + 1
```

⚠️ Watch out: `(a, b) -> a - b` overflows for large negative numbers. Use `Integer.compare(a, b)` for safety when values can be extreme.

---

## Your Recurring Mistakes — Group 2
- **Stack type**: using `Stack<>` instead of `Deque<>` / `ArrayDeque<>`
- **Monotonic stack**: storing values instead of indices — can't fill result array
- **Level-order BFS**: capturing `queue.size()` inside the loop instead of before it
- **Multi-source BFS**: seeding sources one at a time instead of all upfront
- **Heap comparator**: integer subtraction overflow for extreme values

---

## Solved Card Examples
- Stack: `evaluate-reverse-polish-notation-solved.md` · `valid-parentheses-solved.md`
- Monotonic Stack: `next-greater-element-i-solved.md` · `sliding-window-maximum-solved.md`
- BFS level-order: `maximum-width-binary-tree-solved.md` · `binary-tree-right-side-view-solved.md`
- Multi-source BFS: `rotting-oranges-solved.md` · `walls-and-gates-solved.md`
- Heap: `merge-k-sorted-lists-solved.md` · `find-median-data-stream-solved.md`
- BFS trick (carry-forward): `nested-list-weight-sum-ii-solved.md`
