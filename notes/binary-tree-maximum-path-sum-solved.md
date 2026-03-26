# Binary Tree Maximum Path Sum — Hard
Problem Link: https://leetcode.com/problems/binary-tree-maximum-path-sum/description/
Solved Date: 2026-03-05
Pattern Tag: tree / dfs / post-order / path-sum / global-max
Review Date: 2026-03-12

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-06
- Last Rating: Strong
- Review Count: 1
- Graduated: No

## Real World Analogy
Think of a road network in a city. You want to find the most profitable delivery route. At any intersection (node), you can collect toll revenue. You must pick a contiguous route — it can go left, right, or bend through a junction. But once a route "bends" at a junction, it can't continue upward — a truck can't go in two directions at once. You track the best total revenue seen across all possible routes.

## Core Insight
At every node, answer two different questions: "what's the best path that *arches through* me?" (for the global max) and "what's the best *single-arm extension* I can offer my parent?" — these are not the same.

## Approach
Do a post-order DFS so each node knows its children's best contributions before deciding. At each node, compute the arch (using both sides) to update the global max, but return only the best single-arm extension to the parent since a path cannot branch.

## Mental Model

```
┌─────────────────────────┬──────────────────────────────────────────────────────┐
│ Decision                │ Why                                                  │
├─────────────────────────┼──────────────────────────────────────────────────────┤
│ Use Math.max(0, child)  │ A negative subtree hurts the sum — just don't        │
│                         │ extend into it. Treat it as 0 (don't go there).      │
├─────────────────────────┼──────────────────────────────────────────────────────┤
│ currSum uses both sides │ We're asking "is the best path an arch through me?"  │
│ (left + node + right)   │ This is valid as a complete path — not extendable.   │
├─────────────────────────┼──────────────────────────────────────────────────────┤
│ Return uses one side    │ Parent can only extend us in one direction. Returning │
│ max(left, right) + node │ both would let parent "re-bend" an already bent path. │
├─────────────────────────┼──────────────────────────────────────────────────────┤
│ Global maxSum variable  │ The best path may not pass through the root. We must  │
│ updated at every node   │ track the best answer seen across ALL nodes.          │
├─────────────────────────┼──────────────────────────────────────────────────────┤
│ Init to MIN_VALUE       │ All nodes could be negative. Can't init to 0 or we'd  │
│                         │ incorrectly return 0 for an all-negative tree.        │
└─────────────────────────┴──────────────────────────────────────────────────────┘
```

## Pseudocode
```
globalMax = Integer.MIN_VALUE

dfs(node):
  if node == null → return 0

  leftGain  = max(0, dfs(node.left))   // ignore if negative
  rightGain = max(0, dfs(node.right))  // ignore if negative

  archSum = node.val + leftGain + rightGain   // best path through this node
  globalMax = max(globalMax, archSum)         // update global answer

  return node.val + max(leftGain, rightGain)  // best single-arm for parent

return globalMax
```

## Complexity
- Time: O(N) — every node is visited exactly once
- Space: O(H) — recursion stack, where H is tree height; O(log N) balanced, O(N) worst case (skewed)

## Watch Out For
- Initialize `maxSum` to `Integer.MIN_VALUE`, not `0` — tree can be all negatives
- `Math.max(0, childSum)` clamps negative subtrees, NOT negative node values
- The return value and the `currSum` are intentionally different — don't conflate them
- Best path may not pass through root — always update global max at every node

## Dry Run

Tree:
```
        -10
       /    \
      9      20
            /  \
           15    7
```

| Node | leftSum | rightSum | currSum (arch)   | maxSum | returns       |
|------|---------|----------|------------------|--------|---------------|
| 9    | 0       | 0        | 9+0+0 = 9        | 9      | 9             |
| 15   | 0       | 0        | 15+0+0 = 15      | 15     | 15            |
| 7    | 0       | 0        | 7+0+0 = 7        | 15     | 7             |
| 20   | 15      | 7        | 20+15+7 = **42** | **42** | 20+15 = 35    |
| -10  | 9       | 35       | -10+9+35 = 34    | 42     | -10+35 = 25   |

Answer: **42** (path: 15 → 20 → 7)

## Boilerplate Template
```java
class Solution {
    int globalMax = Integer.MIN_VALUE;

    public int maxPathSum(TreeNode root) {
        dfs(root);
        return globalMax;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;

        int leftGain  = Math.max(0, dfs(node.left));
        int rightGain = Math.max(0, dfs(node.right));

        // arch: best complete path through this node (update global)
        globalMax = Math.max(globalMax, node.val + leftGain + rightGain);

        // single-arm: best extension we can offer the parent
        return node.val + Math.max(leftGain, rightGain);
    }
}
```
