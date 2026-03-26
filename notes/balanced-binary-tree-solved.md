# Balanced Binary Tree — Easy
Problem Link: https://leetcode.com/problems/balanced-binary-tree/
Solved Date: 2026-03-24
Pattern Tag: tree / dfs / post-order / height-check

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like checking that every floor of a building is structurally balanced — if any section has one wing more than 1 floor taller than the other, the whole thing fails.

## Core Insight
Post-order DFS that returns actual height or -1 (sentinel for "unbalanced"). If any subtree returns -1, propagate -1 upward immediately.

## Approach
Helper returns height if subtree is balanced, or -1 if not. At each node: get left and right heights; if either is -1, return -1; if abs(left - right) > 1, return -1; otherwise return 1 + max(left, right).

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Return -1 as sentinel (not boolean)  │ Combines "is balanced?" and "what's   │
│                                      │ the height?" in one return value      │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Short-circuit on -1 from children    │ If subtree is unbalanced, don't waste │
│                                      │ time computing parent's height         │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Check |left - right| > 1 at node    │ Balance is checked bottom-up — every  │
│                                      │ node must satisfy the condition       │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
checkHeight(node):
    if node == null: return 0
    leftH = checkHeight(node.left)
    if leftH == -1: return -1   // short-circuit
    rightH = checkHeight(node.right)
    if rightH == -1: return -1  // short-circuit
    if abs(leftH - rightH) > 1: return -1
    return 1 + max(leftH, rightH)

isBalanced(root): return checkHeight(root) != -1
```

## Complexity
- Time: O(n) — visit each node once
- Space: O(h) — recursion stack

## Watch Out For
- A naive O(n²) approach calls `height()` at every node separately — avoid it
- Check for -1 BEFORE computing the difference
- Null node returns 0 (height 0), not -1

## Dry Run
```
    1
   / \
  2   3
 / \
4   5

checkHeight(4)=1, checkHeight(5)=1
checkHeight(2): |1-1|=0 ✓ → return 2
checkHeight(3)=1
checkHeight(1): |2-1|=1 ✓ → return 3
isBalanced = true ✓
```

## Boilerplate Template
```java
class Solution {
    public boolean isBalanced(TreeNode root) {
        return checkHeight(root) != -1;
    }

    private int checkHeight(TreeNode node) {
        if (node == null) return 0;
        int left = checkHeight(node.left);
        if (left == -1) return -1;
        int right = checkHeight(node.right);
        if (right == -1) return -1;
        if (Math.abs(left - right) > 1) return -1;
        return 1 + Math.max(left, right);
    }
}
```
