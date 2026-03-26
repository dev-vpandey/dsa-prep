# Construct Binary Tree from Preorder and Inorder Traversal — Medium
Problem Link: https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/
Solved Date: 2026-03-25
Pattern Tag: tree / recursion / divide-conquer / index-mapping

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like reconstructing a family tree from two lists — the first member in the "visit order" list is always the family head (root). Their position in the "left-to-right" list tells you how many relatives are on each side.

## Core Insight
First element of preorder is always the root. Find root in inorder to determine left and right subtree sizes. Recurse on left and right sublists of both arrays.

## Approach
HashMap for O(1) inorder index lookup. Recursive: `build(preStart, inStart, inEnd)`. Root = `preorder[preStart]`. `rootIdx = inorderMap[root]`. Left subtree size = `rootIdx - inStart`. Recurse left and right with adjusted indices.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ HashMap for inorder index            │ O(1) lookup vs O(n) linear scan;      │
│                                      │ called n times = O(n) vs O(n²)        │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ leftSize = rootIdx - inStart         │ Number of nodes in left subtree;      │
│                                      │ determines how to split preorder      │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Left preorder: [preStart+1,          │ Preorder is: root, [left subtree],    │
│ preStart+1+leftSize-1]               │ [right subtree] — left block starts   │
│                                      │ at preStart+1 with leftSize elements  │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
inorderMap = {val → index} for inorder
preIdx = 0  // global or pass as param

build(inStart, inEnd):
    if inStart > inEnd: return null
    rootVal = preorder[preIdx++]
    root = new TreeNode(rootVal)
    mid = inorderMap[rootVal]
    root.left = build(inStart, mid-1)
    root.right = build(mid+1, inEnd)
    return root
```

## Complexity
- Time: O(n) — each node created once, O(1) lookup
- Space: O(n) — HashMap + recursion stack

## Watch Out For
- Build the HashMap in constructor/before recursion (not inside recursive call)
- Process LEFT subtree before RIGHT (preorder: root, left, right)
- Use a mutable preorder index (`preIdx`) — shared across all recursive calls

## Dry Run
`preorder=[3,9,20,15,7]`, `inorder=[9,3,15,20,7]`
```
build(0,4): root=3(preIdx=1), mid=1
  left=build(0,0): root=9(preIdx=2), mid=0 → no children → return Node(9)
  right=build(2,4): root=20(preIdx=3), mid=3
    left=build(2,2): root=15(preIdx=4), mid=2 → return Node(15)
    right=build(4,4): root=7(preIdx=5), mid=4 → return Node(7)
    return Node(20, left=15, right=7)
return Node(3, left=9, right=Node(20,15,7)) ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    private int preIdx = 0;
    private Map<Integer, Integer> inorderMap;
    private int[] preorder;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) inorderMap.put(inorder[i], i);
        return build(0, inorder.length - 1);
    }

    private TreeNode build(int inStart, int inEnd) {
        if (inStart > inEnd) return null;
        int rootVal = preorder[preIdx++];
        TreeNode root = new TreeNode(rootVal);
        int mid = inorderMap.get(rootVal);
        root.left = build(inStart, mid - 1);  // left before right (preorder)
        root.right = build(mid + 1, inEnd);
        return root;
    }
}
```
