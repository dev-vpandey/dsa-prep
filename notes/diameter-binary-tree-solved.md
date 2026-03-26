# Diameter of Binary Tree — Easy
Problem Link: https://leetcode.com/problems/diameter-of-binary-tree/
Solved Date: 2026-03-24
Pattern Tag: tree / dfs / post-order / global-max

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like finding the longest trail through a forest where you can only move along tree branches — the longest path goes through some node where the left branch length plus the right branch length is maximized.

## Core Insight
At every node, the diameter passing through it = leftHeight + rightHeight. Track the global maximum during post-order DFS. Return height (not diameter) upward.

## Approach
DFS helper returns height. At each node: compute left and right heights; update global max with `left + right`; return `1 + max(left, right)` as height. The answer is the global max.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Return height, not diameter          │ Parent needs height to compute its    │
│ from recursive call                  │ own diameter; global max tracks answer│
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Update global max at every node      │ Diameter may not pass through root — │
│                                      │ could be entirely in a subtree        │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Diameter = leftH + rightH            │ Count edges: left path + right path   │
│ (not +2 for current node)            │ through current node                  │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
maxDiameter = 0

dfs(node):
    if node == null: return 0
    left = dfs(node.left)
    right = dfs(node.right)
    maxDiameter = max(maxDiameter, left + right)
    return 1 + max(left, right)

dfs(root)
return maxDiameter
```

## Complexity
- Time: O(n) — visit each node once
- Space: O(h) — recursion stack

## Watch Out For
- Update `maxDiameter` inside DFS, not in the main function
- A single-node tree has diameter 0 (no edges)
- The diameter path may not pass through the root

## Dry Run
```
    1
   / \
  2   3
 / \
4   5

dfs(4)=1, dfs(5)=1
dfs(2): left=1, right=1, max=max(0,2)=2, return 2
dfs(3)=1
dfs(1): left=2, right=1, max=max(2,3)=3, return 3
Result: 3 ✓
```

## Boilerplate Template
```java
class Solution {
    private int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        dfs(root);
        return maxDiameter;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;
        int left = dfs(node.left);
        int right = dfs(node.right);
        maxDiameter = Math.max(maxDiameter, left + right); // update global max
        return 1 + Math.max(left, right);                  // return height
    }
}
```
