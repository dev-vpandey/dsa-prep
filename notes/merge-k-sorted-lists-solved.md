# Merge k Sorted Lists — Hard
Problem Link: https://leetcode.com/problems/merge-k-sorted-lists/
Solved Date: 2026-03-25
Pattern Tag: heap / min-heap / k-way-merge / linked-list

## SRS Tracking
- Stage: 4
- Review Date: 2026-04-07
- Last Rating: Strong
- Review Count: 4
- Graduated: No

---

# Real World Analogy
Like merging k sorted queues into one — always take the smallest head from any queue. A priority queue tells you which queue has the smallest head without scanning all k.

## Core Insight
Min-heap of size k (one node per list). Always extract the minimum, add it to the result, then push the next node from that same list into the heap.

## Approach
Add the head of each non-null list to a min-heap. Use a dummy node. While heap non-empty: poll min node, append to result, push `node.next` if it exists. Return dummy.next.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Min-heap ordered by node.val         │ O(log k) extraction vs O(k) linear   │
│                                      │ scan at each step                    │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Push node.next after polling         │ Only advance one list at a time —     │
│                                      │ the one whose minimum was just used   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Dummy node for result chain          │ Avoids head-null special case when    │
│                                      │ building the result list              │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
heap = min-heap ordered by node.val
for each list head: if not null, heap.add(head)

dummy = new Node(0)
curr = dummy
while heap not empty:
    node = heap.poll()
    curr.next = node
    curr = curr.next
    if node.next != null: heap.add(node.next)

return dummy.next
```

## Complexity
- Time: O(n log k) — n total nodes, each heap op is O(log k)
- Space: O(k) — heap size

## Watch Out For
- Custom comparator: `(a, b) -> a.val - b.val` for min-heap of ListNodes
- Skip null list heads when initializing
- Push `node.next` only if not null

## Dry Run
`lists = [1→4→5, 1→3→4, 2→6]`
```
heap: {1(L1), 1(L2), 2(L3)}
poll 1(L1) → push 4; heap={1(L2),2(L3),4(L1)}
poll 1(L2) → push 3; heap={2(L3),3(L2),4(L1)}
poll 2(L3) → push 6; heap={3(L2),4(L1),6(L3)}
poll 3(L2) → push 4; heap={4(L1),4(L2),6(L3)}
...
Result: 1→1→2→3→4→4→5→6 ✓
```

## Boilerplate Template
```java
import java.util.*;

class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (ListNode head : lists) {
            if (head != null) heap.offer(head);
        }

        ListNode dummy = new ListNode(0), curr = dummy;
        while (!heap.isEmpty()) {
            ListNode node = heap.poll();
            curr.next = node;
            curr = curr.next;
            if (node.next != null) heap.offer(node.next);
        }
        return dummy.next;
    }
}
```
