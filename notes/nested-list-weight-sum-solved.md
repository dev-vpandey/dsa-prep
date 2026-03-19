# Nested List Weight Sum — Medium
Link: https://leetcode.com/problems/nested-list-weight-sum/
Solved Date: 2026-03-03
Review Date: 2026-03-10

## Core Insight
BFS naturally maps each traversal level to a depth, so any integer found
at BFS level N gets multiplied by N — no need to track depth manually per element.

## Approach
Initialize the queue with all elements of the input list at depth 1.
Each BFS level corresponds to one depth — process every element: if it's
an integer, accumulate (value × depth); if it's a list, expand its children
into the back of the queue. Increment depth after each level completes.

## Mental Model
┌─────────────────────────┬────────────────────────────────────────────────┐
│ Decision                │ Why                                            │
├─────────────────────────┼────────────────────────────────────────────────┤
│ BFS over DFS            │ BFS level == depth naturally, no extra param   │
├─────────────────────────┼────────────────────────────────────────────────┤
│ Queue<NestedInteger>    │ Each slot is one element, not a whole list     │
├─────────────────────────┼────────────────────────────────────────────────┤
│ addAll() not add()      │ add() re-queues the list itself → infinite     │
│ when expanding a list   │ loop; addAll() queues the children inside it   │
├─────────────────────────┼────────────────────────────────────────────────┤
│ depth var, not          │ levelSize = count of elements at this level,   │
│ levelSize as depth      │ NOT the depth; needs its own counter from 1    │
└─────────────────────────┴────────────────────────────────────────────────┘

## Pseudocode
```
init queue with all elements of nestedList
depth = 1
while queue not empty:
    levelSize = queue.size()
    for i in 0..levelSize:
        element = queue.poll()
        if element.isInteger():
            sum += element.getInteger() × depth
        else:
            queue.addAll(element.getList())
    depth++
return sum
```

## Complexity
- Time: O(N) — every NestedInteger element is enqueued and dequeued exactly once
- Space: O(N) — queue holds at most all N elements at once (worst case: flat list)

## Watch Out For
- Using `levelSize` as depth — it's the element count, not the depth
- `queue.add(element)` when element is a list → re-queues the same list → infinite loop
- Forgetting `depth++` after the inner for-loop → everything multiplied by 1

## Dry Run
Input: `[[1,1], 2, [1,1]]`

After init:
  Queue (front→back): [ [1,1] | 2 | [1,1] ]
  depth=1, levelSize=3

Level 1 — process 3 elements:
  Poll [1,1] → list  → addAll(1,1)   Queue: [ 2 | [1,1] | 1 | 1 ]
  Poll 2     → int   → sum += 2×1=2  Queue: [ [1,1] | 1 | 1 ]
  Poll [1,1] → list  → addAll(1,1)   Queue: [ 1 | 1 | 1 | 1 ]
  depth++ → depth=2

Level 2 — process 4 elements:
  Poll 1 → int → sum += 1×2=2  (sum=4)
  Poll 1 → int → sum += 1×2=2  (sum=6)
  Poll 1 → int → sum += 1×2=2  (sum=8)
  Poll 1 → int → sum += 1×2=2  (sum=10)
  depth++ → depth=3

Queue empty → return 10 ✅

## Pattern Tag
bfs / level-order / nested-structure / weighted-depth

## Boilerplate Template
```java
public int depthSum(List<NestedInteger> nestedList) {
    var sum = 0;
    Queue<NestedInteger> queue = new LinkedList<>(nestedList);
    var depth = 1;
    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        for (var i = 0; i < levelSize; i++) {
            var element = queue.poll();
            if (element.isInteger())
                sum += element.getInteger() * depth;
            else
                queue.addAll(element.getList());
        }
        depth++;
    }
    return sum;
}
```
