# Maximum Width of Binary Tree — Medium
Problem Link: https://leetcode.com/problems/maximum-width-of-binary-tree/
Solved Date: 2026-03-24
Pattern Tag: bfs / level-order / index-tracking / width-calculation

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like measuring the width of a family tree diagram where empty spots in a generation still count — the width is the distance between the leftmost and rightmost non-null members of any generation.

## Core Insight
Assign indices to nodes (root=0, left child=2i+1, right child=2i+2). Width of each level = last_index - first_index + 1. Normalize by subtracting first index to prevent overflow.

## Approach
BFS level by level. Each BFS entry is (node, index). At each level: width = last.index - first.index + 1. When enqueueing children, use normalized indices relative to current level's first node.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Normalize: subtract level's first    │ Indices grow exponentially (2^depth)  │
│ index from all indices at that level │ → overflow without normalization      │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ left child = 2*i+1 (before norm)     │ Standard 0-indexed binary tree index  │
│ → normalized: 2*(i-offset)           │ mapping                               │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Width = last - first + 1             │ Includes both endpoints and all null  │
│                                      │ nodes between them                    │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
queue = [(root, 0)]
maxWidth = 0
while queue not empty:
    levelSize = queue.size()
    firstIndex = queue.front().index
    for i in 0..levelSize-1:
        (node, idx) = queue.dequeue()
        normalizedIdx = idx - firstIndex     // prevent overflow
        if i == levelSize-1:
            maxWidth = max(maxWidth, normalizedIdx + 1)
        if node.left: queue.enqueue((node.left, 2*normalizedIdx))
        if node.right: queue.enqueue((node.right, 2*normalizedIdx+1))
return maxWidth
```

## Complexity
- Time: O(n) — visit each node once
- Space: O(n) — BFS queue

## Watch Out For
- Normalize at each level to prevent Long overflow (even `long` can overflow at deep trees)
- Width includes null gaps between leftmost and rightmost nodes
- Track first index of each level BEFORE the inner loop

## Dry Run
```
    1(idx=0)
   / \
  3   2
(1)   (2)

Level 0: first=0, last=0, width=1
Level 1: first=1, normalize: 3→0, 2→1; last=1, width=2 ✓
maxWidth = 2
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public int widthOfBinaryTree(TreeNode root) {
        int maxWidth = 0;
        Queue<long[]> queue = new LinkedList<>(); // [node_addr, index] — use object array
        // Store as Deque<int[]> after casting node
        Deque<Object[]> bfsQ = new ArrayDeque<>();
        bfsQ.offer(new Object[]{root, 0L});

        while (!bfsQ.isEmpty()) {
            int size = bfsQ.size();
            long firstIdx = ((Long) bfsQ.peekFirst()[1]);
            long lastIdx = firstIdx;

            for (int i = 0; i < size; i++) {
                Object[] entry = bfsQ.poll();
                TreeNode node = (TreeNode) entry[0];
                long idx = (Long) entry[1] - firstIdx; // normalize
                if (i == size - 1) lastIdx = idx;

                if (node.left != null) bfsQ.offer(new Object[]{node.left, 2 * idx});
                if (node.right != null) bfsQ.offer(new Object[]{node.right, 2 * idx + 1});
            }
            maxWidth = (int) Math.max(maxWidth, lastIdx + 1);
        }
        return maxWidth;
    }
}
```
