# Flatten Binary Tree to Linked List — Medium
Problem Link: https://leetcode.com/problems/flatten-binary-tree-to-linked-list/
Solved Date: 2026-03-24
Pattern Tag: tree / linked-list / preorder / in-place

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like unrolling a spiral notebook — each time you encounter a left branch, you insert it inline between the current page and the right section, then continue rolling forward.

## Core Insight
For each node with a left subtree: find the rightmost node of the left subtree, attach the right subtree to it, move left to right, null the left. Repeat going down the right.

## Approach
Iterative. At each node: if left exists, find the rightmost node of left subtree (`predecessor`). Set `predecessor.next = curr.right`. Move left to right. Set left = null. Advance curr to curr.right.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Attach right subtree to predecessor  │ Rightmost of left subtree is the last │
│ (rightmost of left subtree)          │ in preorder before right subtree      │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Move left to right, null the left    │ Flatten: left must be null, chain     │
│                                      │ continues through right pointers      │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ In-place, no extra space             │ Modify tree structure directly;       │
│                                      │ Morris traversal variant              │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
curr = root
while curr != null:
    if curr.left != null:
        predecessor = curr.left
        while predecessor.right != null:
            predecessor = predecessor.right
        predecessor.right = curr.right   // attach right subtree
        curr.right = curr.left           // move left to right
        curr.left = null                 // null the left
    curr = curr.right                    // advance
```

## Complexity
- Time: O(n) — each node visited at most twice
- Space: O(1) — in-place, no recursion stack

## Watch Out For
- Find the RIGHTMOST of the left subtree (not just left.right)
- Set `curr.right = curr.left` BEFORE nulling left
- The iterative approach avoids O(h) recursion stack

## Dry Run
```
    1
   / \
  2   5
 / \   \
3   4   6

curr=1, left=2: rightmost of {2,3,4}=4 → 4.right=5, 1.right=2, 1.left=null
Tree: 1→2(→3,4→5→6)
curr=2, left=3: rightmost of {3}=3 → 3.right=4, 2.right=3, 2.left=null
curr=3, no left → curr=4
curr=4, no left → curr=5
...
Result: 1→2→3→4→5→6 ✓
```

## Boilerplate Template
```java
class Solution {
    public void flatten(TreeNode root) {
        TreeNode curr = root;
        while (curr != null) {
            if (curr.left != null) {
                // find rightmost node of left subtree
                TreeNode predecessor = curr.left;
                while (predecessor.right != null) predecessor = predecessor.right;

                predecessor.right = curr.right; // attach right subtree to predecessor
                curr.right = curr.left;          // move left subtree to right
                curr.left = null;                // null the left pointer
            }
            curr = curr.right; // advance down the flattened chain
        }
    }
}
```
