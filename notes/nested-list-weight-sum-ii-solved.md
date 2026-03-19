# Nested List Weight Sum II — Medium
Link: [https://leetcode.com/problems/nested-list-weight-sum-ii/](https://leetcode.com/problems/nested-list-weight-sum-ii/)
Solved Date: 2026-03-03
Review Date: 2026-03-06

## Core Insight
Instead of computing `value × (maxDepth - depth + 1)` directly, maintain a running sum across BFS levels — each integer naturally gets counted once per remaining level, eliminating the need to know maxDepth upfront.

## Approach
BFS level by level. At each level, collect the sum of integers into `levelSum`, accumulate into `runningSum`, then add `runningSum` to `totalSum`. Because `runningSum` carries forward every integer seen so far, shallower integers get added more times than deeper ones — exactly matching inverse depth weighting.

## Mental Model

```
┌─────────────────────────────────────────────────────────────────────┐
│                    Key Decision                                      │
├────────────────────────┬────────────────────────────────────────────┤
│ Why not compute weight │ maxDepth is unknown until traversal ends;  │
│ = maxDepth-depth+1     │ forces two-pass or reformulation           │
├────────────────────────┼────────────────────────────────────────────┤
│ Why runningSum works   │ val×N == val added N times; adding         │
│                        │ runningSum once per level counts each      │
│                        │ integer once per remaining level           │
├────────────────────────┼────────────────────────────────────────────┤
│ Why levelSum separate  │ Only integers contribute to runningSum,    │
│ from queue expansion   │ nested lists just get expanded             │
├────────────────────────┼────────────────────────────────────────────┤
│ When to increment      │ After full level is processed, not inside  │
│ runningSum             │ the per-element loop                       │
└────────────────────────┴────────────────────────────────────────────┘
```

## Pseudocode
```
totalSum = 0, runningSum = 0
queue = all top-level elements

while queue not empty:
    levelSize = queue.size()
    levelSum = 0

    for i in 0..levelSize:
        curr = queue.poll()
        if curr.isInteger():
            levelSum += curr.getInteger()
        else:
            queue.addAll(curr.getList())

    runningSum += levelSum
    totalSum += runningSum

return totalSum
```

## Complexity
- Time: O(N) — every NestedInteger visited exactly once
- Space: O(N) — queue holds at most one full level at a time

## Watch Out For
- In the two-pass approach: `maxDepth` must start at 0, not 1 — the loop increments after every level including the last, so starting at 1 overshoots by 1
- `levelSum` only accumulates integers — nested lists get enqueued, not summed
- `runningSum += levelSum` and `totalSum += runningSum` must happen after the full level loop, not inside it

## Dry Run

Input: `[1, [4, [6]]]`

```
Initial queue: [1, [4,[6]]]

Level 1:
  poll 1        → integer, levelSum = 1
  poll [4,[6]]  → list, enqueue 4 and [6]
  runningSum = 0 + 1 = 1
  totalSum   = 0 + 1 = 1

Level 2:
  poll 4        → integer, levelSum = 4
  poll [6]      → list, enqueue 6
  runningSum = 1 + 4 = 5
  totalSum   = 1 + 5 = 6

Level 3:
  poll 6        → integer, levelSum = 6
  runningSum = 5 + 6 = 11
  totalSum   = 6 + 11 = 17 ✓
```

Why it matches weights: `1` added 3 times (3), `4` added 2 times (8), `6` added 1 time (6) → 17

## Pattern Tag
`bfs / level-order / carry-forward / inverse-weighting / running-sum`

## Boilerplate Template
```java
// Template: BFS with carry-forward accumulation
// Use when: weight depends on global value (maxDepth, total) unknown until end
// Trick: reformulate multiplication as repeated addition via running sum

public int bfsCarryForward(List<NestedInteger> nestedList) {
    int totalSum = 0;
    int runningSum = 0;

    Queue<NestedInteger> queue = new LinkedList<>(nestedList);

    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        int levelSum = 0;

        for (int i = 0; i < levelSize; i++) {
            NestedInteger curr = queue.poll();

            if (curr.isInteger()) {
                levelSum += curr.getInteger();     // collect at this level
            } else {
                queue.addAll(curr.getList());      // expand for next level
            }
        }

        runningSum += levelSum;    // carry this level's integers forward
        totalSum += runningSum;    // each prior integer counted again
    }

    return totalSum;
}
```
