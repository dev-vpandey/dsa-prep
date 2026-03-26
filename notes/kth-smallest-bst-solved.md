# Kth Smallest Element in a BST — Medium
Problem Link: https://leetcode.com/problems/kth-smallest-element-in-a-bst/
Solved Date: 2026-03-24
Pattern Tag: bst / inorder-traversal / kth-element

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like reading a sorted dictionary — BST inorder gives alphabetical (sorted) order. The kth word you read is the kth smallest.

## Core Insight
Inorder traversal of a BST yields elements in sorted order. Count down from k; the node where counter hits 0 is the kth smallest. Iterative version is preferred to stop early.

## Approach
Iterative inorder using a stack. Push nodes to the left, process at pop time, decrement k. When k reaches 0, return current node's value.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Inorder = sorted for BST             │ Left subtree < root < right subtree   │
│                                      │ → inorder gives ascending order       │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Iterative to stop early              │ Recursive visits all nodes; iterative │
│                                      │ stops at kth node without going right │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Process node at pop, not push        │ Push = "discovered"; pop = inorder    │
│                                      │ position (all left subtree processed) │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
stack = [], curr = root
while curr != null OR stack not empty:
    while curr != null:
        stack.push(curr)
        curr = curr.left
    curr = stack.pop()
    k--
    if k == 0: return curr.val
    curr = curr.right
```

## Complexity
- Time: O(h + k) — go down to leftmost O(h), then traverse k nodes
- Space: O(h) — stack depth

## Watch Out For
- Decrement k AFTER popping (at the visit point)
- Stop traversal immediately when k hits 0 (no need to process rest)

## Dry Run
```
    3
   / \
  1   4
   \
    2

Inorder: 1 → 2 → 3 → 4
k=1: pop 1 → k=0 → return 1 ✓
k=2: pop 1 → k=1, pop 2 → k=0 → return 2 ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int kthSmallest(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            if (--k == 0) return curr.val; // kth smallest found
            curr = curr.right;
        }
        return -1; // unreachable if k is valid
    }
}
```
