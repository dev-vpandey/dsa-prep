# Remove Nth Node from End of List — Medium
Problem Link: https://leetcode.com/problems/remove-nth-node-from-end-of-list/
Solved Date: 2026-03-24
Pattern Tag: linked-list / two-pointer / dummy-node / gap-trick

## SRS Tracking
- Stage: 3
- Review Date: 2026-03-31
- Last Rating: Okay
- Review Count: 2
- Graduated: No

---

# Real World Analogy
Like two runners on a track where one starts n steps ahead — when the lead runner finishes, the trailing runner is exactly n steps from the finish line.

## Core Insight
Create a gap of exactly `n+1` nodes between fast and slow using a dummy node before head. When fast reaches null, slow.next is the node to delete.

## Approach
Add dummy node before head. Advance `fast` n+1 steps from dummy. Then advance both `slow` and `fast` together until fast is null. `slow.next` is the target — `slow.next = slow.next.next`.

## Mental Model
```
┌──────────────────────────────────────┬───────────────────────────────────────┐
│ Decision                             │ Why                                   │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Dummy node before head               │ Handles deleting the head node        │
│                                      │ without special-casing it             │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Advance fast n+1 steps (not n)       │ Need slow to stop ONE BEFORE the      │
│                                      │ target, so we can do slow.next = skip │
├──────────────────────────────────────┼───────────────────────────────────────┤
│ Advance from dummy (not head)        │ Dummy is position -1; n+1 steps from  │
│                                      │ dummy puts fast at position n         │
└──────────────────────────────────────┴───────────────────────────────────────┘
```

## Pseudocode
```
dummy = new Node(0, head)
fast = dummy, slow = dummy
advance fast n+1 steps

while fast != null:
    fast = fast.next
    slow = slow.next

slow.next = slow.next.next  // skip the target
return dummy.next
```

## Complexity
- Time: O(n) — single pass
- Space: O(1)

## Watch Out For
- Advance `fast` exactly `n+1` steps from dummy (not `n`)
- Dummy prevents NPE when deleting the head node
- `slow.next = slow.next.next` — not `slow = slow.next`

## Dry Run
`1→2→3→4→5`, n=2 (remove 4th from start = 2nd from end)
```
dummy→1→2→3→4→5
fast advances n+1=3 steps from dummy: fast=3 (at node 3)
Both advance: slow=1,fast=4 → slow=2,fast=5 → slow=3,fast=null
slow=3, slow.next=4 → slow.next=5
Result: 1→2→3→5 ✓
```

## Boilerplate Template
```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(0, head);
        ListNode fast = dummy, slow = dummy;

        // advance fast n+1 steps ahead
        for (int i = 0; i <= n; i++) fast = fast.next;

        // move both until fast is null
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        // slow is one before the target
        slow.next = slow.next.next;
        return dummy.next;
    }
}
```
