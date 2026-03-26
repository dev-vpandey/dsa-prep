# Linked List Cycle — Easy
Problem Link: https://leetcode.com/problems/linked-list-cycle/
Solved Date: 2026-03-24
Pattern Tag: linked-list / Floyd / cycle-detection / slow-fast-pointer

## SRS Tracking
- Stage: 5
- Review Date: 2026-04-23
- Last Rating: Strong
- Review Count: 8
- Graduated: No

---

# Real World Analogy
Two runners on a circular track — if one runs faster, they will eventually lap the slower runner. If the track has no loop, the faster runner just finishes first and is never seen again.

## Core Insight
If a cycle exists, fast and slow pointers will eventually meet inside the cycle. If fast reaches null, no cycle.

## Approach
`slow` moves 1 step, `fast` moves 2. If they ever point to the same node, there's a cycle. If `fast` or `fast.next` becomes null, list ends with no cycle.

## Mental Model
```
┌────────────────────────────────┬─────────────────────────────────────────────┐
│ Decision                       │ Why                                         │
├────────────────────────────────┼─────────────────────────────────────────────┤
│ Check fast != null AND         │ fast jumps 2 — need to guard both fast      │
│ fast.next != null              │ and fast.next to avoid NPE                  │
├────────────────────────────────┼─────────────────────────────────────────────┤
│ Meeting proves cycle           │ In a cycle, fast gains 1 node per step on   │
│                                │ slow — they must collide eventually         │
├────────────────────────────────┼─────────────────────────────────────────────┤
│ fast reaching null = no cycle  │ A finite list always ends at null —         │
│                                │ fast hits null before slow catches up       │
└────────────────────────────────┴─────────────────────────────────────────────┘
```

## Pseudocode
```
slow = head
fast = head
while fast != null AND fast.next != null:
    slow = slow.next
    fast = fast.next.next
    if slow == fast:
        return true
return false
```

## Complexity
- Time: O(n) — fast traverses at most n + cycle_length steps
- Space: O(1) — two pointers

## Watch Out For
- Guard `fast != null` before `fast.next != null`
- Check meeting AFTER advancing, not before

## Dry Run
`3 → 2 → 0 → -4 → (back to 2)`
```
slow=3, fast=3
slow=2, fast=0  (fast: 3→2→0)
slow=0, fast=2  (fast: 0→-4→2)
slow=-4, fast=-4 → MATCH → return true ✓

[1 → 2 → null] (no cycle)
slow=1, fast=1
slow=2, fast=null → exit loop → return false ✓
```

## Boilerplate Template
```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) return true;
        }
        return false;
    }
}
```
