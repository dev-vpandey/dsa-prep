# Validate Binary Search Tree — Medium
Problem Link: https://leetcode.com/problems/validate-binary-search-tree/
Solved Date: 2026-03-24
Pattern Tag: bst / dfs / range-validation / min-max

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like a security checkpoint with tightening rules — as you go left, the ceiling drops (you must be smaller than current root); as you go right, the floor rises. Every node must fit within its inherited constraints.

## Core Insight
Pass valid range `(min, max)` down the tree. Each node must satisfy `min < node.val < max`. Going left: max becomes current val. Going right: min becomes current val.

## Approach
Recursive DFS with `(node, min, max)`. Initial call: `validate(root, Long.MIN_VALUE, Long.MAX_VALUE)`. For each node: check `min < val < max`; recurse left with `(node, min, val)` and right with `(node, val, max)`.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Pass range bounds, not just parent   │ Naive check (left < root < right)     │
│ value                                │ fails: [5,4,6,null,null,3,7] → 3     │
│                                      │ looks valid locally but 3 < 5 breaks  │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Use Long.MIN/MAX_VALUE               │ Node values can be Integer.MIN/MAX —  │
│                                      │ need bounds outside int range         │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Strict inequality (not >=)           │ BST definition: left < root < right,  │
│                                      │ no equal values                       │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
validate(node, min, max):
    if node == null: return true
    if node.val <= min OR node.val >= max: return false
    return validate(node.left, min, node.val) AND
           validate(node.right, node.val, max)

isValidBST(root): return validate(root, Long.MIN, Long.MAX)
```

## Complexity
- Time: O(n) — visit every node
- Space: O(h) — recursion stack

## Watch Out For
- Don't just compare with parent — need the full inherited range
- Use `Long` bounds, not `Integer` (values can be `Integer.MIN_VALUE`)
- Strict `<` and `>` (no equal values allowed in BST)

## Dry Run
```
    5
   / \
  1   4
     / \
    3   6

validate(5, MIN, MAX): 5 in range ✓
  validate(1, MIN, 5): 1 in range ✓ → both children null → true
  validate(4, 5, MAX): 4 <= 5 → FALSE ✓ (correctly detected)
Result: false ✓
```

## Boilerplate Template
```java
class Solution {
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean validate(TreeNode node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return validate(node.left, min, node.val) &&
               validate(node.right, node.val, max);
    }
}
```
