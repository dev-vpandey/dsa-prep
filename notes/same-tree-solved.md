# Same Tree — Easy
Problem Link: https://leetcode.com/problems/same-tree/
Solved Date: 2026-03-24
Pattern Tag: tree / dfs / structural-comparison

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-23
- Last Rating: Strong
- Review Count: 8
- Graduated: No

---

# Real World Analogy
Like comparing two identical-looking filing systems — two cabinets are identical only if every drawer, at every level, in every position, has the exact same label.

## Core Insight
Two trees are the same if: both null, OR both non-null with equal values AND same(left, left) AND same(right, right). Any mismatch anywhere returns false.

## Approach
Recursive DFS. Base cases: both null → true; one null → false; values differ → false. Recurse on left pair and right pair — both must be true.

## Mental Model
```
┌────────────────────────────────┬─────────────────────────────────────────────┐
│ Decision                       │ Why                                         │
├────────────────────────────────┼─────────────────────────────────────────────┤
│ Check both-null first          │ Valid base case — two absent subtrees match │
├────────────────────────────────┼─────────────────────────────────────────────┤
│ Then check one-null            │ Structural mismatch — one has a child the   │
│                                │ other doesn't                               │
├────────────────────────────────┼─────────────────────────────────────────────┤
│ AND on both recursive calls    │ Both left pair AND right pair must match    │
└────────────────────────────────┴─────────────────────────────────────────────┘
```

## Pseudocode
```
isSame(p, q):
    if p == null AND q == null: return true
    if p == null OR q == null: return false
    if p.val != q.val: return false
    return isSame(p.left, q.left) AND isSame(p.right, q.right)
```

## Complexity
- Time: O(n) — visit each node pair once
- Space: O(h) — recursion stack

## Watch Out For
- Order matters: check both-null before one-null to avoid NPE
- Values can be negative — compare with `!=`, not object equality

## Dry Run
```
p: 1 → left=2, right=3
q: 1 → left=2, right=3

isSame(1,1): vals equal → isSame(2,2) AND isSame(3,3)
isSame(2,2): vals equal → isSame(null,null) AND isSame(null,null) → true AND true = true
isSame(3,3): same → true
Result: true ✓
```

## Boilerplate Template
```java
class Solution {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;
        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
```
