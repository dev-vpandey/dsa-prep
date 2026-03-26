# Symmetric Tree — Easy
Problem Link: https://leetcode.com/problems/symmetric-tree/
Solved Date: 2026-03-24
Pattern Tag: tree / dfs / mirror-check / structural-comparison

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like checking if a butterfly's wings are mirror images — the left wing's outer edge must match the right wing's outer edge, and their inner edges must match each other.

## Core Insight
Check if the left subtree mirrors the right subtree. `isMirror(left, right)`: outer children must match, inner children must match — recurse `isMirror(left.left, right.right)` AND `isMirror(left.right, right.left)`.

## Approach
Helper `isMirror(p, q)`: both null → true; one null → false; values differ → false; recurse with outer-outer and inner-inner pairs.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ isMirror(left.left, right.right)     │ Outer edges of the mirror             │
│ AND isMirror(left.right, right.left) │ AND inner edges — both must match     │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Start with isMirror(root.left,       │ Root is trivially symmetric with      │
│ root.right)                          │ itself; only subtrees need checking   │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
isSymmetric(root): return isMirror(root.left, root.right)

isMirror(p, q):
    if p == null AND q == null: return true
    if p == null OR q == null: return false
    if p.val != q.val: return false
    return isMirror(p.left, q.right) AND isMirror(p.right, q.left)
```

## Complexity
- Time: O(n) — visit every node once
- Space: O(h) — recursion stack

## Watch Out For
- Cross comparison: `(p.left, q.right)` and `(p.right, q.left)` — NOT `(p.left, q.left)`
- Can also solve iteratively with a queue of node pairs

## Dry Run
```
    1
   / \
  2   2
 / \ / \
3  4 4  3

isMirror(2,2): 2==2 ✓ → isMirror(3,3) AND isMirror(4,4)
isMirror(3,3): both leaves, equal → true
isMirror(4,4): both leaves, equal → true
Result: true ✓
```

## Boilerplate Template
```java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;
        return isMirror(p.left, q.right) && isMirror(p.right, q.left);
    }
}
```
