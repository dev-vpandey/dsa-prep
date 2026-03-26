# Binary Tree Right Side View — Medium
Problem Link: https://leetcode.com/problems/binary-tree-right-side-view/description/
Solved Date: 2026-03-11
Pattern Tag: bfs / level-order / dfs / reverse-preorder / right-side-view
Review Date: 2026-03-18

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-22
- Last Rating: —
- Review Count: 0
- Graduated: No

## Real World Analogy
Standing to the right of a building and looking at each floor — you only see
the rightmost room on each floor, even if the right wing is shorter than the left.

## Core Insight
At every level of the tree, only the rightmost node is visible — either capture
it last in BFS, or visit right-before-left in DFS and take the first node seen
at each new depth.

## Approach
**BFS:** Level-order traversal; snapshot `queue.size()` before processing each
level, then record the last node polled in that batch.

**DFS (Reverse Pre-order):** Recurse right-first. The first time `level == res.size()`,
you're seeing a new depth for the first time — and since right was visited first,
it's always the rightmost node.

## Mental Model
┌─────────────────────────┬─────────────────────────────────────────────┐
│ Decision                │ Why                                         │
├─────────────────────────┼─────────────────────────────────────────────┤
│ BFS: snapshot levelSize │ queue grows as children are added; must     │
│ before the for-loop     │ freeze the count before processing begins   │
├─────────────────────────┼─────────────────────────────────────────────┤
│ BFS: check i==levelSize-│ Last node polled in the level = rightmost   │
│ 1 to add to result      │ visible node from the right side            │
├─────────────────────────┼─────────────────────────────────────────────┤
│ DFS: visit right child  │ Right-first guarantees that at any new      │
│ before left child       │ depth, the first node seen is rightmost     │
├─────────────────────────┼─────────────────────────────────────────────┤
│ DFS: level == res.size()│ res.size() == number of levels seen so far; │
│ as the "first visit"    │ equality means this depth is brand new      │
└─────────────────────────┴─────────────────────────────────────────────┘

## Pseudocode
### BFS
```
queue = [root]
while queue not empty:
  levelSize = queue.size()
  for i in 0..levelSize:
    node = queue.poll()
    add node.left, node.right if not null
    if i == levelSize - 1: res.add(node.val)
```

### DFS (Reverse Pre-order)
```
dfs(node, level):
  if node == null: return
  if level == res.size(): res.add(node.val)  // first visit at this depth
  dfs(node.right, level + 1)
  dfs(node.left,  level + 1)
```

## Complexity
- Time: O(n) — every node visited exactly once in both approaches
- Space: O(n) — BFS queue holds up to n/2 nodes (last level); DFS stack is O(h) ≤ O(n)

## Watch Out For
- BFS: snapshot `queue.size()` BEFORE the for-loop, not inside it
- DFS: recurse right BEFORE left — reversing this gives left-side view
- Both: single-node tree and null root are handled naturally

## Dry Run
Tree:
        1
       / \
      2   3
       \   \
        5   4

**BFS:**
Level 0: poll 1 → add 2, 3 → last = 1 ✓
Level 1: poll 2, 3 → add 5, 4 → last = 3 ✓
Level 2: poll 5, 4 → last = 4 ✓
Result: [1, 3, 4]

**DFS (right-first):**
visit(1, 0) → res=[1]
  visit(3, 1) → res=[1,3]
    visit(4, 2) → res=[1,3,4]
    visit(null, 3) → return
  visit(2, 1) → level 1 already seen, skip
    visit(null, 2) → return
    visit(5, 2) → level 2 already seen, skip
Result: [1, 3, 4]

## Boilerplate Template
```java
// BFS Level-Order Template
public List<Integer> levelOrderResult(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    if (root == null) return res;
    Queue<TreeNode> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
        int levelSize = queue.size();       // snapshot BEFORE the loop
        for (int i = 0; i < levelSize; i++) {
            TreeNode node = queue.poll();
            if (node.left  != null) queue.add(node.left);
            if (node.right != null) queue.add(node.right);
            if (i == levelSize - 1) res.add(node.val); // ← swap condition for your goal
        }
    }
    return res;
}

// DFS Right-First Template
public List<Integer> dfsRightFirst(TreeNode root) {
    List<Integer> res = new ArrayList<>();
    dfs(root, res, 0);
    return res;
}
private void dfs(TreeNode node, List<Integer> res, int level) {
    if (node == null) return;
    if (level == res.size()) res.add(node.val); // first visit at this depth
    dfs(node.right, res, level + 1);            // right before left
    dfs(node.left,  res, level + 1);
}
```
