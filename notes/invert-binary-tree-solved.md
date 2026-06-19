# Invert Binary Tree — Easy
Problem Link: https://leetcode.com/problems/invert-binary-tree/
Solved Date: 2026-03-24
Pattern Tag: tree / dfs / recursion / swap

## SRS Tracking
- Stage: 6
- Review Date: 2026-08-26
- Last Rating: Strong
- Review Count: 9
- Graduated: Yes

---

# Real World Analogy
Like holding a tree up to a mirror — every left branch becomes a right branch and vice versa, recursively all the way down.

## Core Insight
Swap the left and right children at every node, then recursively invert both subtrees. The recursion handles the rest.

## Approach
Post-order or pre-order DFS. At each node: recursively invert left and right subtrees, then swap the results. Base case: null node returns null.

## Mental Model
```
┌──────────────────────────────┬───────────────────────────────────────────────┐
│ Decision                     │ Why                                           │
├──────────────────────────────┼───────────────────────────────────────────────┤
│ Swap children, then recurse  │ Either order (pre or post) works — swap       │
│ OR recurse then swap         │ at any point, recursion handles subtrees      │
├──────────────────────────────┼───────────────────────────────────────────────┤
│ Base case: return null       │ Null nodes have nothing to swap — safe        │
│                              │ to return immediately                         │
├──────────────────────────────┼───────────────────────────────────────────────┤
│ Return root                  │ Structure is modified in-place; return root   │
│                              │ to give caller the (unchanged) root reference │
└──────────────────────────────┴───────────────────────────────────────────────┘
```

## Pseudocode
```
invert(root):
    if root == null: return null
    root.left = invert(root.right)
    root.right = invert(root.left)   // BUG: left already changed above!

// Correct version:
invert(root):
    if root == null: return null
    left = invert(root.left)
    right = invert(root.right)
    root.left = right
    root.right = left
    return root
```

## Complexity
- Time: O(n) — visit every node once
- Space: O(h) — recursion stack, h = tree height (O(log n) balanced, O(n) skewed)

## Watch Out For
- Do NOT do `root.left = invert(root.right); root.right = invert(root.left)` — after first line, root.left is already changed, so second line inverts the wrong subtree
- Save both recursive results first, then assign

## Dry Run
```
     4
   /   \
  2     7
 / \   / \
1   3 6   9

After invert:
     4
   /   \
  7     2
 / \   / \
9   6 3   1
```

## Boilerplate Template

**Post-order (card original)** — recurse first, swap on return:
```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }
}
```

**Pre-order (helper style)** — swap first, then recurse. Easier to narrate in interviews:
```java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        swap(root);
        return root;
    }

    private void swap(TreeNode node) {
        if (node == null) return;
        TreeNode temp = node.left;
        node.left = node.right;
        node.right = temp;
        swap(node.left);
        swap(node.right);
    }
}
```
