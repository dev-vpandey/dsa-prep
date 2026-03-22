# Lowest Common Ancestor of a Binary Tree — Medium
Link: [LeetCode](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/description/)
Solved Date: 2026-02-28
Review Date: 2026-03-24

## SRS Tracking
- Stage: 6
- Review Date: 2026-06-20
- Last Rating: Strong
- Review Count: 1
- Graduated: Yes

## Core Insight
If you find p or q, return it immediately — if the other node is a descendant, the found node IS the LCA. If they're on opposite sides, the current node is the LCA.

## Approach
Recurse through the tree. Return a node the moment you find p or q. After recursing left and right, if both sides returned something, current node is the LCA. If only one side returned something, bubble it up — the answer lives in that subtree.

## Pseudocode
```
lca(node, p, q):
    if node == null → return null
    if node == p or node == q → return node

    left  = lca(node.left, p, q)
    right = lca(node.right, p, q)

    if left != null and right != null → return node  (p and q on opposite sides)
    return left != null ? left : right               (one side has both)
```

## Boilerplate Template
```java
private TreeNode lca(TreeNode node, TreeNode p, TreeNode q) {
    if (node == null) return null;
    if (node == p || node == q) return node;

    var left  = lca(node.left, p, q);
    var right = lca(node.right, p, q);

    if (left != null && right != null) return node;
    return left != null ? left : right;
}
```

## Complexity
- Time: O(N) — visit every node once in the worst case
- Space: O(H) — recursion stack depth, H = height of tree (O(log N) balanced, O(N) skewed)

## Watch Out For
- Missing null check inside the recursive helper — `node.left/right` can be null, must handle it
- p and q are guaranteed to exist in the tree — no need to validate
- A node can be a descendant of itself — returning p/q immediately handles this correctly

## Dry Run

**Tree:**
```
          3
        /   \
       5     1
      / \   / \
     6   2 0   8
        / \
       7   4
```

**Case 1: p=5, q=4 (one is ancestor of the other)**
```
lca(3) → lca(5) → finds p=5, returns 5 immediately (never searches under 5)
       → lca(1) → searches entire right subtree, returns null
left=5, right=null → return 5 ✓
```
4 is never explicitly found — but since 5 is returned and 4 lives under 5, 5 IS the LCA.

**Case 2: p=5, q=1 (on opposite sides)**
```
lca(3) → lca(5) → returns 5
       → lca(1) → returns 1
left=5, right=1 → both non-null → return 3 ✓
```

## Mental Model — Why return early when you find p or q?

```
┌────────────────────────────────────┬───────────────────────────────────────────────────────────────┐
│ Scenario                           │ What happens                                                  │
├────────────────────────────────────┼───────────────────────────────────────────────────────────────┤
│ Found p, q is below p              │ Return p immediately — p is already the LCA, no need to find q│
│ Found p, q is elsewhere            │ Bubble p up — other side will find q, parent becomes LCA      │
│ Both sides return non-null         │ Current node is the split point = LCA                         │
│ Only one side returns non-null     │ Both p and q live in that subtree, bubble the result up        │
└────────────────────────────────────┴───────────────────────────────────────────────────────────────┘
```

The recursion **trusts** that if the other side returns null, it found nothing — meaning both nodes are on the side that returned something.

## Pattern Tag
binary-tree / recursion / postorder / lca
