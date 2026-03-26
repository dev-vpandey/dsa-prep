# Maximum Depth of Binary Tree — Easy
Problem Link: https://leetcode.com/problems/maximum-depth-of-binary-tree/
Solved Date: 2026-03-24
Pattern Tag: tree / dfs / recursion / height

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-23
- Last Rating: Strong
- Review Count: 8
- Graduated: No

---

# Real World Analogy
Like counting the floors in a building by going to the deepest room — height = 1 (current floor) + tallest of left wing vs right wing.

## Core Insight
Height of a tree = 1 + max(height(left), height(right)). Base case: null node has height 0.

## Approach
Simple post-order DFS. Recurse into both children, take the max of their heights, add 1 for the current node. Return 0 for null.

## Mental Model
```
┌─────────────────────────────┬────────────────────────────────────────────────┐
│ Decision                    │ Why                                            │
├─────────────────────────────┼────────────────────────────────────────────────┤
│ Return 0 for null           │ A non-existent subtree contributes 0 height   │
├─────────────────────────────┼────────────────────────────────────────────────┤
│ 1 + max(left, right)        │ Current node adds 1 level; take the taller    │
│                             │ child to find the max depth path              │
└─────────────────────────────┴────────────────────────────────────────────────┘
```

## Pseudocode
```
maxDepth(root):
    if root == null: return 0
    return 1 + max(maxDepth(root.left), maxDepth(root.right))
```

## Complexity
- Time: O(n) — visit every node
- Space: O(h) — recursion stack depth

## Watch Out For
- Single-node tree → returns 1 (correct, not 0)
- Can also do iterative BFS counting levels

## Dry Run
```
    3
   / \
  9  20
     / \
    15   7

maxDepth(3) = 1 + max(maxDepth(9), maxDepth(20))
maxDepth(9) = 1 + max(0, 0) = 1
maxDepth(20) = 1 + max(maxDepth(15), maxDepth(7)) = 1 + max(1,1) = 2
maxDepth(3) = 1 + max(1, 2) = 3 ✓
```

## Boilerplate Template
```java
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) return 0;
        return 1 + Math.max(maxDepth(root.left), maxDepth(root.right));
    }
}
```
