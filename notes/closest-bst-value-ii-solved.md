# Closest Binary Search Tree Value II — Hard
Problem Link: https://leetcode.com/problems/closest-binary-search-tree-value-ii/
Solved Date: 2026-03-25
Pattern Tag: bst / inorder / two-deque / sliding-window-on-sorted

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like reading a sorted number line and keeping a sliding window of k numbers closest to a target — each new number you see, decide if it's closer than the farthest number currently in your window.

## Core Insight
BST inorder gives sorted values. Maintain a deque of size k. For each new value in inorder: if deque has k elements, compare which end is farther from target — remove the farther one, add new value.

## Approach
Inorder traversal (iterative or recursive). Maintain `Deque<Integer>` of size k. For each `val`: if `deque.size() == k`, compare `|val - target|` vs `|deque.peekFirst() - target|`. If new val is closer, remove front; else stop (sorted order means remaining vals are even farther). Add val if deque not full.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Compare new val with FRONT of deque  │ Inorder is sorted; deque is a window  │
│                                      │ of consecutive values. Front is       │
│                                      │ always farthest-left (oldest/smallest)│
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Stop adding when new val is farther  │ Sorted order: if current val is       │
│ than front                           │ farther than front, all future vals   │
│                                      │ will be even farther (early exit)     │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Use deque (not just a list)          │ O(1) removal from front when window   │
│                                      │ slides                                │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
deque = ArrayDeque()

inorder(node):
    if node == null: return
    inorder(node.left)
    if deque.size() == k:
        if |node.val - target| >= |deque.front - target|:
            return  // early exit — getting farther
        deque.pollFirst()
    deque.addLast(node.val)
    inorder(node.right)

return new ArrayList(deque)
```

## Complexity
- Time: O(n) — full inorder in worst case, but O(k + log n) in average for balanced BST
- Space: O(k + h) — deque + recursion stack

## Watch Out For
- Early exit when new value is farther than deque front — crucial for efficiency
- Use `>=` not `>` for distance comparison to handle equal distances
- Return the deque as a List

## Dry Run
```
BST:    4
       / \
      2   5
     / \
    1   3
target=3.714286, k=2

Inorder: 1,2,3,4,5
1: deque=[1]
2: deque=[1,2]
3: |3-3.71|=0.71 < |1-3.71|=2.71 → remove 1, deque=[2,3]
4: |4-3.71|=0.29 < |2-3.71|=1.71 → remove 2, deque=[3,4]
5: |5-3.71|=1.29 >= |3-3.71|=0.71 → early exit
Result: [3,4] ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    private Deque<Integer> deque = new ArrayDeque<>();
    private double target;
    private int k;

    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        this.target = target;
        this.k = k;
        inorder(root);
        return new ArrayList<>(deque);
    }

    private void inorder(TreeNode node) {
        if (node == null) return;
        inorder(node.left);
        if (deque.size() == k) {
            // if new value is not closer than the front, we're done
            if (Math.abs(node.val - target) >= Math.abs(deque.peekFirst() - target)) return;
            deque.pollFirst();
        }
        deque.addLast(node.val);
        inorder(node.right);
    }
}
```
