# Vertical Order Traversal of Binary Tree — Hard
Link: https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/description/
Solved Date: 2026-03-04
Review Date: 2026-03-11

## Real World Analogy
Imagine taking a photo of a tree and dropping vertical curtains between columns. All nodes behind the same curtain belong together, ordered top-to-bottom, left-to-right in value when tied.

## Core Insight
BFS gives you row order for free — you just need to track column IDs and sort ties within the same (row, col) by value.

## Approach
Run BFS level by level. For each level, group nodes by column into a local TreeMap and sort values within each column bucket. After each level, merge into the global column map. The global TreeMap gives left-to-right column order automatically.

## Mental Model
```
┌─────────────────────────────────────────────────────────────┐
│ Decision                  │ Why                             │
├───────────────────────────┼─────────────────────────────────┤
│ BFS (not DFS)             │ Gives row ordering for free     │
├───────────────────────────┼─────────────────────────────────┤
│ Local levelMap per level  │ Isolates same-position tie sort │
├───────────────────────────┼─────────────────────────────────┤
│ TreeMap for colMap        │ Auto column order (left→right)  │
├───────────────────────────┼─────────────────────────────────┤
│ Sort within level bucket  │ Same row+col → sort by value    │
├───────────────────────────┼─────────────────────────────────┤
│ Merge after each level    │ Preserves top-row-first order   │
└───────────────────────────┴─────────────────────────────────┘
```

## Pseudocode
```
colMap = TreeMap<col, List<Integer>>
queue = [(root, col=0)]

while queue not empty:
    levelMap = TreeMap<col, List<Integer>>
    process all nodes at this level:
        add node.val to levelMap[col]
        enqueue left child at (col-1), right at (col+1)

    for each col in levelMap:
        sort values
        colMap[col].addAll(values)

return colMap.values()
```

## Complexity
- Time: O(N log N)
- Space: O(N)

## Watch Out For
- `levelMap` must be **local** to each BFS iteration — shared state breaks tie-breaking
- Sort values **before** merging into colMap, not after
- Use `TreeMap` (not `HashMap`) for both maps to avoid manual sorting on columns

## Dry Run
Tree: [3,9,20,null,null,15,7]

```
       3 (col=0)
      / \
     9   20 (col=-1, col=1)
        /  \
       15   7 (col=0, col=2)
```

Level 0: {0: [3]}
Level 1: {-1: [9], 1: [20]}
Level 2: {0: [15], 2: [7]}

colMap after merge:
-1 → [9]
 0 → [3, 15]   ← 15 comes after 3 because it's a lower row
 1 → [20]
 2 → [7]

Result: [[9],[3,15],[20],[7]] ✓

## Pattern Tag
bfs / level-order / column-tracking / treemap / vertical-traversal

## Boilerplate Template
```java
// Vertical/Column-based BFS traversal template
Map<Integer, List<Integer>> colMap = new TreeMap<>();
Queue<NodePair> queue = new LinkedList<>();
queue.add(new NodePair(root, 0));

while (!queue.isEmpty()) {
    Map<Integer, List<Integer>> levelMap = new TreeMap<>();
    int levelSize = queue.size();

    for (int i = 0; i < levelSize; i++) {
        var curr = queue.poll();
        levelMap.computeIfAbsent(curr.col(), k -> new ArrayList<>()).add(curr.node().val);
        if (curr.node().left != null)  queue.add(new NodePair(curr.node().left,  curr.col() - 1));
        if (curr.node().right != null) queue.add(new NodePair(curr.node().right, curr.col() + 1));
    }

    for (var entry : levelMap.entrySet()) {
        Collections.sort(entry.getValue());
        colMap.computeIfAbsent(entry.getKey(), k -> new ArrayList<>()).addAll(entry.getValue());
    }
}

record NodePair(TreeNode node, int col) {}
```
